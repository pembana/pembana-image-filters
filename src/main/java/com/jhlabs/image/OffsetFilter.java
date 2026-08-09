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

import java.awt.Rectangle;

/**
 * A filter that applies an offset transformation to an image, shifting the pixels by a specified
 * horizontal and vertical offset. The offset wraps around the image boundaries unless disabled.
 * This filter is commonly used for creating effects like tiling or mimicking pixel scrolling.
 * <p>
 * The class extends the abstract {@code TransformFilter} and implements the necessary
 * inverse transformation logic. The transformation is applied pixel-by-pixel based on the
 * offsets and wrapping behavior.
 * <p>
 * The filter works by modifying the position of each pixel in the source image based on the
 * specified offsets, while considering the dimensions of the input image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class OffsetFilter extends TransformFilter implements java.io.Serializable {

	static final long serialVersionUID = 8123120922961090736L;

	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private boolean wrap;

	public OffsetFilter() {
		this(0, 0, true);
	}

	public OffsetFilter(int xOffset, int yOffset, boolean wrap) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.wrap = wrap;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getXOffset() {
		return this.xOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getYOffset() {
		return this.yOffset;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	public boolean getWrap() {
		return this.wrap;
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		out[0] = (x + this.width - this.xOffset) % this.width;
		out[1] = (y + this.height - this.yOffset) % this.height;
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		this.width = width;
		this.height = height;
		while (this.xOffset < 0) {
			this.xOffset += width;
		}
		while (this.yOffset < 0) {
			this.yOffset += height;
		}
		this.xOffset %= width;
		this.yOffset %= height;
		return super.filterPixels(width, height, inPixels, transformedSpace);
	}

	@Override
	public String toString() {
		return "Distort/Offset...";
	}
}
