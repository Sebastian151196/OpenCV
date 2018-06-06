/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtarea;

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
public class OpencvTarea {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new OpencvTarea().run();
    }
    
    public void run(){
        
        System.out.println("\nRunning Detection");
        
        //Put All the XML files in different CascadeClassifier
        CascadeClassifier faceDetector = new CascadeClassifier("res/haarcascade_frontalface_default.xml");
        CascadeClassifier eyeDetector = new CascadeClassifier("res/haarcascade_eye.xml");
        CascadeClassifier noseDetector = new CascadeClassifier("res/haarcascade_mcs_nose.xml");
        CascadeClassifier mouthDetector = new CascadeClassifier("res/haarcascade_mcs_mouth.xml");
        
        //Load the image for detection
        Mat image = Imgcodecs.imread("res/a0.png");
       
        //Detect faces, eye, mouth and nose in image
        MatOfRect faceDetections = new MatOfRect();
                                    
        faceDetector.detectMultiScale(image, faceDetections);

        //Draw a bounding box around each face
        for(Rect rect : faceDetections.toArray()){
            Imgproc.rectangle(
                image,
                new Point(rect.x, rect.y),
                new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 0, 255), 2); 
            
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
                    new Scalar(255, 0, 0), 2);
            }
            
            MatOfRect noseDetections = new MatOfRect();
            noseDetector.detectMultiScale(face, noseDetections);
            
            for(Rect rect2 : noseDetections.toArray()){
                Imgproc.rectangle(
                    image,
                    new Point(rect2.x + rect.x, rect2.y + rect.y),
                    new Point(rect.x + rect2.x + rect2.width, rect.y + rect2.y + rect2.height),
                    new Scalar(255, 190, 200), 2);
            }
            
            MatOfRect mouthDetections = new MatOfRect();
            mouthDetector.detectMultiScale(face, mouthDetections);
            
            for(Rect rect3 : mouthDetections.toArray()){
                Imgproc.rectangle(
                    image,
                    new Point(rect3.x + rect.x, rect3.y + rect.y),
                    new Point(rect.x + rect3.x + rect3.width, rect.y + rect3.y + rect3.height),
                    new Scalar(230, 180, 90));
            }
            
        }
        
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        
        
                //Save the visualized detection
        String filename = "res/faceDetection.png";
        System.out.println(String.format("Writing %s", filename));
        
        Imgcodecs.imwrite(filename, image);
    }
    
}
