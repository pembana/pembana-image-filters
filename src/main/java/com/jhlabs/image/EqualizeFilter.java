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
 * A filter to perform auto-equalization on an image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class EqualizeFilter extends WholeImageFilter implements java.io.Serializable {

	protected int[][] lut;

	public EqualizeFilter() {
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		Histogram histogram = new Histogram(inPixels, width, height, 0, width);

		int i;
		int j;

		if (histogram.getNumSamples() > 0) {
			float scale = 255.0f / histogram.getNumSamples();
			this.lut = new int[3][256];
			for (i = 0; i < 3; i++) {
				this.lut[i][0] = histogram.getFrequency(i, 0);
				for (j = 1; j < 256; j++) {
					this.lut[i][j] = this.lut[i][j - 1] + histogram.getFrequency(i, j);
				}
				for (j = 0; j < 256; j++) {
					this.lut[i][j] = Math.round(this.lut[i][j] * scale);
				}
			}
		}
		else {
			this.lut = null;
		}

		i = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				inPixels[i] = filterRGB(x, y, inPixels[i]);
				i++;
			}
		}
		this.lut = null;

		return inPixels;
	}

	public int filterRGB(int x, int y, int rgb) {
		if (this.lut != null) {
			int a = rgb & 0xff000000;
			int r = this.lut[Histogram.RED][(rgb >> 16) & 0xff];
			int g = this.lut[Histogram.GREEN][(rgb >> 8) & 0xff];
			int b = this.lut[Histogram.BLUE][rgb & 0xff];

			return a | (r << 16) | (g << 8) | b;
		}
		return rgb;
	}

	@Override
	public String toString() {
		return "Colors/Equalize";
	}
}
