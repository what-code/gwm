package com.b5m.gwm.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.WritableRaster;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @Author 尹正飞
 * @Email zhengfei.yin@b5m.com
 * @Version 2013-5-14 上午10:34:20
 **/

public class ImageUtil {

	/**
	 * 图像切割（改）
	 * 
	 * @param in
	 *            源图像流
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static BufferedImage abscut(InputStream in, int x, int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(in);
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, srcWidth, srcHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();

				return tag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 压缩图片
	 * 
	 * @param src
	 *            图片文件
	 * @param w
	 *            目标宽
	 * @param h
	 *            目标高
	 * @param per
	 *            百分比
	 */
	public static BufferedImage smallerImage(Image src, int w, int h, float per) {
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // 得到源图长

		double w2 = (old_w * 1.00) / (w * 1.00);
		double h2 = (old_h * 1.00) / (h * 1.00);

		// 图片跟据长宽留白，成一个正方形图。
		BufferedImage oldpic;
		if (old_w > old_h) {
			oldpic = new BufferedImage(old_w, old_w, BufferedImage.TYPE_INT_RGB);
		} else {
			if (old_w < old_h) {
				oldpic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
			} else {
				oldpic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
			}
		}
		Graphics2D g = oldpic.createGraphics();
		g.setColor(Color.white);
		if (old_w > old_h) {
			g.fillRect(0, 0, old_w, old_w);
			g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
		} else {
			if (old_w < old_h) {
				g.fillRect(0, 0, old_h, old_h);
				g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
			} else {
				g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
			}
		}
		g.dispose();
		src = oldpic;
		// 图片调整为方形结束
		if (old_w > w)
			new_w = (int) Math.round(old_w / w2);
		else
			new_w = old_w;
		if (old_h > h)
			new_h = (int) Math.round(old_h / h2);// 计算新图长宽
		else
			new_h = old_h;
		BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
		tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);

		return tag;
	}

	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		// 宽百分比
		double sx = (double) targetW / source.getWidth();
		// 高百分比
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			// sx = sy;
			targetW = (int) (sx * source.getWidth());
			targetH = (int) (sx * source.getHeight());
		} else {
			// sy = sx;
			targetH = (int) (sy * source.getHeight());
			targetW = (int) (sy * source.getWidth());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
}
