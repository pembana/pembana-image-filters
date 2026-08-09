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
 * A filter that applies an oval shape to an image, setting pixels outside the oval to transparent.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class OvalFilter extends PointFilter {

	private float centreX = 0;
	private float centreY = 0;
	private float a = 0;
	private float b = 0;
	private float a2 = 0;
	private float b2 = 0;

	public OvalFilter() {
	}

	@Override
	public void setDimensions(int width, int height) {
		super.setDimensions(width, height);
		final float halfWidth = width / 2.0f;
		final float halfHeight = height / 2.0f;

		this.centreX = halfWidth;
		this.a = halfWidth;

		this.centreY = halfHeight;
		this.b = halfHeight;

		this.a2 = this.a * this.a;
		this.b2 = this.b * this.b;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		float dx = x - this.centreX;
		float dy = y - this.centreY;
		float x2 = dx * dx;
		float y2 = dy * dy;
		if (y2 >= (this.b2 - (this.b2 * x2) / this.a2)) {
			return 0x00000000;
		}
		return rgb;
	}

	@Override
	public String toString() {
		return "Stylize/Oval...";
	}

}
