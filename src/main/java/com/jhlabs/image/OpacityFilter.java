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
 * Sets the opacity (alpha) of every pixel in an image to a constant value.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class OpacityFilter extends PointFilter implements java.io.Serializable {

	static final long serialVersionUID = 5644263685527598345L;

	private int opacity;
	private int opacity24;

	/**
	 * Construct an OpacityFilter with 50% opacity.
	 */
	public OpacityFilter() {
		this(0x88);
	}

	/**
	 * Construct an OpacityFilter with the given opacity (alpha).
	 * @param opacity the opacity (alpha) in the range 0..255
	 */
	public OpacityFilter(int opacity) {
		setOpacity(opacity);
	}

	/**
	 * Set the opacity.
	 * @param opacity the opacity (alpha) in the range 0..255
	 */
	public void setOpacity(int opacity) {
		this.opacity = opacity;
		this.opacity24 = opacity << 24;
	}

	/**
	 * Get the opacity setting.
	 * @return the opacity
	 */
	public int getOpacity() {
		return this.opacity;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		if ((rgb & 0xff000000) != 0) {
			return (rgb & 0xffffff) | this.opacity24;
		}
		return rgb;
	}

	@Override
	public String toString() {
		return "Colors/Transparency...";
	}

}

