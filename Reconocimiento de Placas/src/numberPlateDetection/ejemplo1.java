/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numberPlateDetection;

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
public class ejemplo1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new ejemplo1().run();
    }
    
    public void run(){
        
        System.out.println("\nRunning Detection");
        
        //Put All the XML files in different CascadeClassifier
        CascadeClassifier faceDetector = new CascadeClassifier("resources/findPlate.xml");
        CascadeClassifier eyeDetector = new CascadeClassifier("resources/find0.xml");
        //CascadeClassifier noseDetector = new CascadeClassifier("res/haarcascade_mcs_nose.xml");
        //CascadeClassifier mouthDetector = new CascadeClassifier("res/haarcascade_mcs_mouth.xml");
        
        //Load the image for detection
        Mat image = Imgcodecs.imread("resources/a42.jpg");
       
        //Detect faces, eye, mouth and nose in image
        MatOfRect faceDetections = new MatOfRect();
                                    
        faceDetector.detectMultiScale(image, faceDetections);

        //Draw a bounding box around each face
        for(Rect rect : faceDetections.toArray()){
            Imgproc.rectangle(
                image,
                new Point(rect.x, rect.y),
                new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 0, 255), 5); 
            
            //Creamos una submatriz donde contiene la region de interes 
            Mat face = image.submat(rect); //Region de interes para la submatriz 
            
            MatOfRect eyeDetections = new MatOfRect();
            //Detector with the new face matrix 
            eyeDetector.detectMultiScale(face, eyeDetections);
                        
            for(Rect rect1 : eyeDetections.toArray()){
                Imgproc.rectangle(
                    image,
                    new Point(rect1.x + rect.x, rect1.y + rect.y),
                    new Point(rect.x + rect1.x + rect1.width, rect.y + rect1.y + rect1.height),
                    new Scalar(255, 0, 255), 5);
            }
           
            
        }
        
        System.out.println(String.format("Detected %s plate", faceDetections.toArray().length));
        
        
                //Save the visualized detection
        String filename = "resources/plateDetection1.jpg";
        System.out.println(String.format("Writing %s", filename));
        
        Imgcodecs.imwrite(filename, image);
    }
    
}
