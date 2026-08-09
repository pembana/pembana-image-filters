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
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * A filter to add a border around an image using the supplied Paint, which may be null for no painting.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class BorderFilter extends AbstractBufferedImageOp {

	private int leftBorder;
	private int rightBorder;
	private int topBorder;
	private int bottomBorder;
	private Paint borderPaint;

	/**
	 * Construct a BorderFilter.
	 */
	public BorderFilter() {
	}

	/**
	 * Construct a BorderFilter.
	 * @param leftBorder the width of the left border
	 * @param topBorder the width of the top border
	 * @param rightBorder the width of the right border
	 * @param bottomBorder the width of the bottom border
	 * @param borderPaint the paint to use for the border
	 */
	public BorderFilter(int leftBorder, int topBorder, int rightBorder, int bottomBorder, Paint borderPaint) {
		this.leftBorder = leftBorder;
		this.topBorder = topBorder;
		this.rightBorder = rightBorder;
		this.bottomBorder = bottomBorder;
		this.borderPaint = borderPaint;
	}

	/**
	 * Set the paint to use for the border.
	 * @param leftBorder the paint to use for the border
	 */
	public void setLeftBorder(int leftBorder) {
		this.leftBorder = leftBorder;
	}

	/**
	 * Get the paint to use for the border.
	 * @return the paint to use for the border
	 */
	public int getLeftBorder() {
		return this.leftBorder;
	}

	/**
	 * Set the right border.
	 * @param rightBorder the width of the right border
	 */
	public void setRightBorder(int rightBorder) {
		this.rightBorder = rightBorder;
	}

	/**
	 * Get the right border.
	 * @return the right border
	 */
	public int getRightBorder() {
		return this.rightBorder;
	}

	/**
	 * Set the top border.
	 * @param topBorder the width of the top border
	 */
	public void setTopBorder(int topBorder) {
		this.topBorder = topBorder;
	}

	/**
	 * Get the top border.
	 * @return the top border
	 */
	public int getTopBorder() {
		return this.topBorder;
	}

	/**
	 * Set the bottom border.
	 * @param bottomBorder the width of the bottom border
	 */
	public void setBottomBorder(int bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	/**
	 * Get the bottom border.
	 * @return the bottom border
	 */
	public int getBottomBorder() {
		return this.bottomBorder;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int width = src.getWidth();
		int height = src.getHeight();

		if (dst == null) {
			dst = new BufferedImage(width + this.leftBorder + this.rightBorder, height + this.topBorder + this.bottomBorder, src.getType());
		}
		Graphics2D g = dst.createGraphics();
		if (this.borderPaint != null) {
			g.setPaint(this.borderPaint);
			if (this.leftBorder > 0) {
				g.fillRect(0, 0, this.leftBorder, height);
			}
			if (this.rightBorder > 0) {
				g.fillRect(width - this.rightBorder, 0, this.rightBorder, height);
			}
			if (this.topBorder > 0) {
				g.fillRect(this.leftBorder, 0, width - this.leftBorder - this.rightBorder, this.topBorder);
			}
			if (this.bottomBorder > 0) {
				g.fillRect(this.leftBorder, height - this.bottomBorder, width - this.leftBorder - this.rightBorder, this.bottomBorder);
			}
		}
		g.drawRenderedImage(src, AffineTransform.getTranslateInstance(this.leftBorder, this.rightBorder));
		g.dispose();
		return dst;
	}

	@Override
	public String toString() {
		return "Distort/Border...";
	}
}
