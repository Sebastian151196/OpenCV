/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plates;

/**
 *
 * @author Sebastian
 */
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Sebastian
 */
public class ejemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new ejemplo().run();
    }
    
    public void run(){
        
        System.out.println("\nRunning DetectionPlates");
        CascadeClassifier faceDetector = new CascadeClassifier("resources/findPlate.xml");
        Mat image = Imgcodecs.imread("resources/a42.jpg");
        
        //Detect faces in the image
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(String.format("Detected %s plates", faceDetections.toArray().length));
        
        //Draw a bounding box around each face
        for(Rect rect : faceDetections.toArray()){
            Imgproc.rectangle(
                    image,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 5);
        }
        //Save the visualized detection
        String filename = "resources/plateDetection.png";
        System.out.println(String.format("Writing %s", filename));
        
        Imgcodecs.imwrite(filename, image);
    }
}
