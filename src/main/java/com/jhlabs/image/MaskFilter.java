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

import java.io.Serializable;

/**
 * Applies a bit mask to each ARGB pixel of an image. You can use this for, say, masking out the red channel.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class MaskFilter extends PointFilter implements Serializable {

	private int mask;

	public MaskFilter() {
		this(0xff00ffff);
	}

	public MaskFilter(int mask) {
		this.canFilterIndexColorModel = true;
		setMask(mask);
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public int getMask() {
		return this.mask;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		return rgb & this.mask;
	}

	@Override
	public String toString() {
		return "Mask";
	}

}
