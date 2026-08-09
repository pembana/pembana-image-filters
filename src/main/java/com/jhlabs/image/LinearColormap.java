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
 * A colormap which interpolates linearly between two colors.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class LinearColormap implements Colormap, java.io.Serializable {

	static final long serialVersionUID = 4256182891287368612L;

	private int color1;
	private int color2;

	/**
	 * Construct a color map with a grayscale ramp from black to white.
	 */
	public LinearColormap() {
		this(0xff000000, 0xffffffff);
	}

	/**
	 * Construct a linear color map.
	 *
	 * @param color1 the color corresponding to value 0 in the colormap
	 * @param color2 the color corresponding to value 1 in the colormap
	 */
	public LinearColormap(int color1, int color2) {
		this.color1 = color1;
		this.color2 = color2;
	}

	/**
	 * Set the first color.
	 * @param color1 the color corresponding to value 0 in the colormap
	 */
	public void setColor1(int color1) {
		this.color1 = color1;
	}

	/**
	 * Get the first color.
	 * @return the color corresponding to value 0 in the colormap
	 */
	public int getColor1() {
		return this.color1;
	}

	/**
	 * Set the second color.
	 * @param color2 the color corresponding to value 1 in the colormap
	 */
	public void setColor2(int color2) {
		this.color2 = color2;
	}

	/**
	 * Get the second color.
	 * @return the color corresponding to value 1 in the colormap
	 */
	public int getColor2() {
		return this.color2;
	}

	/**
	 * Convert a value in the range 0..1 to an RGB color.
	 * @param v a value in the range 0..1
	 * @return an RGB color
	 */
	@Override
	public int getColor(float v) {
		return ImageMath.mixColors(ImageMath.clamp(v, 0, 1.0f), this.color1, this.color2);
	}

}
