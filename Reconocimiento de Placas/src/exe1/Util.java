/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exe1;

/**
 *
 * @author Sebastian
 */
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Util {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public Util() {
	}

	public void setImage(String name, JLabel label1, JPanel listpossible) {
		listpossible.removeAll();
		
		String file = name;
		Mat src = Imgcodecs.imread(file);

		String xmlFile = "resources/cascade.xml";
		CascadeClassifier classifier = new CascadeClassifier(xmlFile);

		// Detecting the face in the snap
		MatOfRect faceDetections = new MatOfRect();
		
		classifier.detectMultiScale(src, faceDetections);
		System.out.println(String.format("Detected %s plate", faceDetections.toArray().length));

		// Drawing boxes
		int n = 0;
		for (Rect rect : faceDetections.toArray()) {

			Imgproc.rectangle(src, // where to draw the box
					new Point(rect.x, rect.y), // bottom left
					new Point(rect.x + rect.width, rect.y + rect.height), // top right
					new Scalar(0, 0, 255), 3 // RGB colour
			);

			Rect roi = new Rect(rect.x, rect.y, rect.width, rect.height);
			Mat cropped = new Mat(src, roi);

			Imgcodecs.imwrite("temp.jpg", cropped);

			// Encoding the image
			MatOfByte matOfByte = new MatOfByte();
			Imgcodecs.imencode(".jpg", cropped, matOfByte);

			// Storing the encoded Mat in a byte array
			byte[] byteArray = matOfByte.toArray();

			// Preparing the Buffered Image
			InputStream in = new ByteArrayInputStream(byteArray);
			try {
				BufferedImage bufImage = ImageIO.read(in);
				JLabel l = new JLabel("Imagen " + n);
				l.setSize(new Dimension(200, 100));

				l.setIcon(new ImageIcon(bufImage));
				listpossible.add(l);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			n++;

		}

		byte[] data = new byte[src.rows() * src.cols() * (int) (src.elemSize())];

		src.get(0, 0, data);

		BufferedImage bufferedImage = new BufferedImage(src.cols(), src.rows(), BufferedImage.TYPE_3BYTE_BGR);

		bufferedImage.getRaster().setDataElements(0, 0, src.cols(), src.rows(), data);

		
		ImageIcon image = new ImageIcon(bufferedImage);
		Image img = image.getImage();
		Image newImg = img.getScaledInstance(label1.getWidth(), label1.getHeight(), Image.SCALE_SMOOTH);
	
		label1.setIcon(new ImageIcon(newImg));

		System.out.println("Image Processed");

		if (faceDetections.toArray().length == 0) {
			Mat dst = new Mat();
			Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
			Imgproc.GaussianBlur(dst.clone(), dst, new Size(3, 3), 0);
			Imgproc.threshold(dst.clone(), dst, 150, 400, Imgproc.THRESH_BINARY);

			List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			Imgproc.findContours(dst.clone(), contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
			MatOfPoint2f approxCurve = new MatOfPoint2f();
			int x = 1;
			for (int i = 0; i < contours.size(); i++) {
				MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
				double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
				Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
				MatOfPoint points = new MatOfPoint(approxCurve.toArray());
				Rect rect = Imgproc.boundingRect(points);
				if (rect.height > 50 && rect.height < 100) {
					Imgproc.rectangle(dst, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 255), 3);
					Rect roi = new Rect(rect.x, rect.y, rect.width, rect.height);
					Mat cropped = new Mat(dst, roi);
					Imgcodecs.imwrite("temp.jpg", cropped);

					// Encoding the image
					MatOfByte matOfByte = new MatOfByte();
					Imgcodecs.imencode(".jpg", cropped, matOfByte);

					// Storing the encoded Mat in a byte array
					byte[] byteArray = matOfByte.toArray();

					// Preparing the Buffered Image
					InputStream in = new ByteArrayInputStream(byteArray);
					try {
						BufferedImage bufImage = ImageIO.read(in);
						JLabel l = new JLabel("Imagen " + x);
						l.setSize(new Dimension(200, 100));
						l.setIcon(new ImageIcon(bufImage));
						listpossible.add(l);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					
					
					x++;
				}

			}

			System.out.println("Numero de X " + x);

		}
		
		listpossible.revalidate();
		listpossible.repaint(); 

	}

}
