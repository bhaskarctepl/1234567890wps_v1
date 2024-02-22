package com.hillspet.wearables.common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class ThumbnailGenerator {
	
	private static final String IMAGEMAT = "png";
	private static final String ROTATE = "rotate";
	private static final Logger LOGGER = LogManager.getLogger(GCPClientUtil.class);
	
	/**
	  * The middle frame of the default captured video is the cover
	 */
	public static final int MOD = 2;
	
	public static File generate(String filePath,InputStream inputStream) throws IOException {
		LOGGER.debug("ThumbnailGenerator = " + filePath);
		String thumbnailName = filePath.substring(filePath.lastIndexOf("\\")+1);
		String thumbNailFilePath = //File.separator +
				 (thumbnailName.replace("mp4", "png").replace("MOV", "png"));
		LOGGER.debug("thumbnailName = " + thumbnailName);
		LOGGER.debug("thumbNailFilePath = " + thumbNailFilePath);
		return imageGenerator(filePath, 2, thumbNailFilePath);
	}
	

	/**
	  * Get video thumbnails
	  * @param filePath: video path
	  * @param mod: video length / mod gets the few frames
	 * @throws Exception
	 */
	public static File imageGenerator(String filePath, int mod,String targetFilePath) throws Exception {
		LOGGER.debug("Inside imageGenerator");
		FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
		File file = null;
		ff.start();
		Frame f = ff.grabImage();
		file = doExecuteFrame(f, targetFilePath);
		ff.stop();
		LOGGER.debug("End imageGenerator");
		return file;
	}
	

	/**
	  * Capture thumbnails
	 * @param f
	  * @param targerFilePath: cover image
	 */
	public static File doExecuteFrame(Frame f, String targerFilePath) {
		LOGGER.debug("Inside doExecuteFrame");
		if (null == f || null == f.image) {
			return null;
		}
		Java2DFrameConverter converter = new Java2DFrameConverter();
		BufferedImage bi = converter.getBufferedImage(f);
		int scaledWidth = 150;
		int scaledHeight = (int) (bi.getHeight() * ( (double) scaledWidth / bi.getWidth()));
	    Image resized =  bi.getScaledInstance( scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
	    BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);
	    buffered.getGraphics().drawImage(resized, 0, 0 , null);
	    LOGGER.debug("targerFilePath " + targerFilePath);
		File output = new File(targerFilePath);
		try {
			LOGGER.debug("Before rendering image");
			ImageIO.write(buffered, IMAGEMAT, output);
			LOGGER.debug("after rendering image");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Error occured while rendering image " , e);
		}
		LOGGER.debug("Returned from doExecuteFrame");
		return output;
	}
}
