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

/**
 * A filter which replaces one color by another in an image. This is frankly, not often useful, but has its occasional
 * uses when dealing with GIF transparency and the like.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class MapColorsFilter extends PointFilter {

	private final int oldColor;
	private final int newColor;

	public MapColorsFilter(int oldColor, int newColor) {
		this.canFilterIndexColorModel = true;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		if (rgb == this.oldColor) {
			return this.newColor;
		}
		return rgb;
	}
}

