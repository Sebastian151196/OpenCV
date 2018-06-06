/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sebastian
 */
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class App 
{
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	public static void main(String[] args) throws Exception {
		String filePath = "res/zebra.jpg";
		
		
		Mat newImage = Imgcodecs.imread(filePath);

		if(newImage.dataAddr()==0){
			System.out.println("Couldn't open file " + filePath);
		}else{

			GUI gui = new GUI("Filtro de ruido", newImage);
			gui.init();
		}
		return;
	}
}