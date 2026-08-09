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
 * Given a binary image, this filter performs binary erosion, setting all removed pixels to the given 'new' color.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ErodeFilter extends BinaryFilter {

	protected int threshold = 2;

	public ErodeFilter() {
		this.newColor = 0xffffffff;
	}

	/**
	 * Set the threshold - the number of neighbouring pixels for dilation to occur.
	 * @param threshold the new threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * Return the threshold - the number of neighbouring pixels for dilation to occur.
	 * @return the current threshold
	 */
	public int getThreshold() {
		return this.threshold;
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		int[] outPixels = new int[width * height];

		for (int i = 0; i < this.iterations; i++) {
			int index = 0;

			if (i > 0) {
				int[] t = inPixels;
				inPixels = outPixels;
				outPixels = t;
			}
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = inPixels[y * width + x];
					if (this.blackFunction.isBlack(pixel)) {
						int neighbours = 0;

						for (int dy = -1; dy <= 1; dy++) {
							int iy = y + dy;
							int ioffset;
							if (0 <= iy && iy < height) {
								ioffset = iy * width;
								for (int dx = -1; dx <= 1; dx++) {
									int ix = x + dx;
									if (!(dy == 0 && dx == 0) && 0 <= ix && ix < width) {
										int rgb = inPixels[ioffset + ix];
										if (!this.blackFunction.isBlack(rgb)) {
											neighbours++;
										}
									}
								}
							}
						}

						if (neighbours >= this.threshold) {
							if (this.colormap != null) {
								pixel = this.colormap.getColor((float) i / this.iterations);
							}
							else {
								pixel = this.newColor;
							}
						}
					}
					outPixels[index++] = pixel;
				}
			}
		}

		return outPixels;
	}

	@Override
	public String toString() {
		return "Binary/Erode...";
	}

}

