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
 * The MaximumFilter class is used to apply a maximum filter effect to an image.
 * This filter processes each pixel of an image by comparing its value to those
 * of its neighboring pixels within a 3x3 grid and retains the maximum value
 * among them. It can be used to amplify bright regions in images or reduce
 * noise.
 * <p>
 * The filter operation is implemented in the {@code filterPixels} method, which
 * manipulates the image data at the pixel level.
 * <p>
 * This class extends {@code WholeImageFilter}, meaning it requires the entire
 * image to be in memory for processing.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 *
 */
public class MaximumFilter extends WholeImageFilter {

	public MaximumFilter() {
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		int index = 0;
		int[] outPixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = 0xff000000;
				for (int dy = -1; dy <= 1; dy++) {
					int iy = y + dy;
					int ioffset;
					if (0 <= iy && iy < height) {
						ioffset = iy * width;
						for (int dx = -1; dx <= 1; dx++) {
							int ix = x + dx;
							if (0 <= ix && ix < width) {
								pixel = PixelUtils.combinePixels(pixel, inPixels[ioffset + ix], PixelUtils.MAX);
							}
						}
					}
				}
				outPixels[index++] = pixel;
			}
		}
		return outPixels;
	}

	@Override
	public String toString() {
		return "Blur/Maximum";
	}

}

