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
 * The LifeFilter class is a specialized implementation of the {@link BinaryFilter} that applies
 * a processing algorithm inspired by Conway's Game of Life rules. This filter operates
 * on a black-and-white image, treating 'black' pixels as alive and 'white' pixels as dead.
 * The algorithm determines the next state of each pixel based on the number of black
 * neighboring pixels.
 * <p>
 * Key features of this filter:
 * <ul>
 * <li>Visualization of cell behavior in a black-and-white paradigm.
 * <li>Determines pixel state transitions based on the Game of Life rules.
 * <li>Processes the image iteratively.
 * </ul>
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class LifeFilter extends BinaryFilter {

	public LifeFilter() {
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		int index = 0;
		int[] outPixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = 0;
				int g = 0;
				int b = 0;
				int pixel = inPixels[y * width + x];
				int a = pixel & 0xff000000;
				int neighbours = 0;

				for (int row = -1; row <= 1; row++) {
					int iy = y + row;
					int ioffset;
					if (0 <= iy && iy < height) {
						ioffset = iy * width;
						for (int col = -1; col <= 1; col++) {
							int ix = x + col;
							if (!(row == 0 && col == 0) && 0 <= ix && ix < width) {
								int rgb = inPixels[ioffset + ix];
								if (this.blackFunction.isBlack(rgb)) {
									neighbours++;
								}
							}
						}
					}
				}

				if (this.blackFunction.isBlack(pixel)) {
					outPixels[index++] = (neighbours == 2 || neighbours == 3) ? pixel : 0xffffffff;
				}
				else {
					outPixels[index++] = (neighbours == 3) ? 0xff000000 : pixel;
				}
			}

		}
		return outPixels;
	}

	@Override
	public String toString() {
		return "Binary/Life";
	}

}

