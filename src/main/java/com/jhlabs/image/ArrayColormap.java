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
 * A colormap implemented with an array of colors. This corresponds to the IndexColorModel class.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ArrayColormap implements Colormap, Cloneable, java.io.Serializable {

	static final long serialVersionUID = -7990431442314209043L;

	/**
	 * The array of colors.
	 */
	protected int[] map;

	/**
	 * Construct an all-black colormap.
	 */
	public ArrayColormap() {
		this.map = new int[256];
	}

	/**
	 * Construct a colormap with the given map.
	 *
	 * @param map the array of ARGB colors
	 */
	public ArrayColormap(int[] map) {
		this.map = map;
	}

	@Override
	public Object clone() {
		try {
			ArrayColormap g = (ArrayColormap) super.clone();
			g.map = this.map.clone();
			return g;
		}
		catch (CloneNotSupportedException ex) {
		}
		return null;
	}

	public void setMap(int[] map) {
		this.map = map;
	}

	public int[] getMap() {
		return this.map;
	}

	/**
	 * Convert a value in the range 0..1 to an RGB color.
	 * @param v a value in the range 0..1
	 * @return an RGB color
	 */
	@Override
	public int getColor(float v) {
		/*
				v *= 255;
				int n = (int)v;
				float f = v-n;
				if (n < 0)
					return map[0];
				else if (n >= 255)
					return map[255];
				return ImageMath.mixColors(f, map[n], map[n+1]);
		*/
		int n = (int) (v * 255);
		if (n < 0) {
			n = 0;
		}
		else if (n > 255) {
			n = 255;
		}
		return this.map[n];
	}

	/**
	 * Set the color at "index" to "color". Entries are interpolated linearly from
	 * the existing entries at "firstIndex" and "lastIndex" to the new entry.
	 * firstIndex < index < lastIndex must hold.
	 * @param index      the index of the color to set
	 * @param firstIndex the first index of the range to interpolate
	 * @param lastIndex  the last index of the range to interpolate
	 * @param color      the ARGBcolor to set
	 */
	public void setColorInterpolated(int index, int firstIndex, int lastIndex, int color) {
		int firstColor = this.map[firstIndex];
		int lastColor = this.map[lastIndex];
		for (int i = firstIndex; i <= index; i++) {
			this.map[i] = ImageMath.mixColors((float) (i - firstIndex) / (index - firstIndex), firstColor, color);
		}
		for (int i = index; i < lastIndex; i++) {
			this.map[i] = ImageMath.mixColors((float) (i - index) / (lastIndex - index), color, lastColor);
		}
	}

	public void setColorRange(int firstIndex, int lastIndex, int color1, int color2) {
		for (int i = firstIndex; i <= lastIndex; i++) {
			this.map[i] = ImageMath.mixColors((float) (i - firstIndex) / (lastIndex - firstIndex), color1, color2);
		}
	}

	public void setColorRange(int firstIndex, int lastIndex, int color) {
		for (int i = firstIndex; i <= lastIndex; i++) {
			this.map[i] = color;
		}
	}

	/**
	 * Set the color at "index" to "color".
	 * @param index the index of the color to set
	 * @param color the ARGB color to set
	 */
	public void setColor(int index, int color) {
		this.map[index] = color;
	}

}
