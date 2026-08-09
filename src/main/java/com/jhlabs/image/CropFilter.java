/*
 * MIT License
 *
 * Copyright (c) 2025 Jerry Huxtable
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.jhlabs.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * A filter which crops an image to a given rectangle.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class CropFilter extends AbstractBufferedImageOp {

	private int x;
	private int y;
	private int width;
	private int height;

	public CropFilter() {
		this(0, 0, 32, 32);
	}

	public CropFilter(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return this.width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int w = src.getWidth();
		int h = src.getHeight();

		if (dst == null) {
			ColorModel dstCM = src.getColorModel();
			dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(this.width, this.height), dstCM.isAlphaPremultiplied(), null);
		}

		Graphics2D g = dst.createGraphics();
		g.drawRenderedImage(src, AffineTransform.getTranslateInstance(-this.x, -this.y));
		g.dispose();

		return dst;
	}

	@Override
	public String toString() {
		return "Distort/Crop";
	}
}
