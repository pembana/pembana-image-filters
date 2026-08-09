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
 * This filter tries to apply the Swing "flush 3D" effect to the black lines in an image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class Flush3DFilter extends WholeImageFilter {

	public Flush3DFilter() {
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		int index = 0;
		int[] outPixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = inPixels[y * width + x];

				if (pixel != 0xff000000 && y > 0 && x > 0) {
					int count = 0;
					if (inPixels[y * width + x - 1] == 0xff000000) {
						count++;
					}
					if (inPixels[(y - 1) * width + x] == 0xff000000) {
						count++;
					}
					if (inPixels[(y - 1) * width + x - 1] == 0xff000000) {
						count++;
					}
					if (count >= 2) {
						pixel = 0xffffffff;
					}
				}
				outPixels[index++] = pixel;
			}

		}
		return outPixels;
	}

	@Override
	public String toString() {
		return "Stylize/Flush 3D...";
	}

}

