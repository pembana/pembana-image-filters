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
 * A filter which draws a gradient interpolated between four colors defined at the corners of the image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class FourColorFilter extends PointFilter {

	private int width;
	private int height;
	private int colorNW;
	private int colorNE;
	private int colorSW;
	private int colorSE;
	private int rNW;
	private int gNW;
	private int bNW;
	private int rNE;
	private int gNE;
	private int bNE;
	private int rSW;
	private int gSW;
	private int bSW;
	private int rSE;
	private int gSE;
	private int bSE;

	public FourColorFilter() {
		setColorNW(0xffff0000);
		setColorNE(0xffff00ff);
		setColorSW(0xff0000ff);
		setColorSE(0xff00ffff);
	}

	public void setColorNW(int color) {
		this.colorNW = color;
		this.rNW = (color >> 16) & 0xff;
		this.gNW = (color >> 8) & 0xff;
		this.bNW = color & 0xff;
	}

	public int getColorNW() {
		return this.colorNW;
	}

	public void setColorNE(int color) {
		this.colorNE = color;
		this.rNE = (color >> 16) & 0xff;
		this.gNE = (color >> 8) & 0xff;
		this.bNE = color & 0xff;
	}

	public int getColorNE() {
		return this.colorNE;
	}

	public void setColorSW(int color) {
		this.colorSW = color;
		this.rSW = (color >> 16) & 0xff;
		this.gSW = (color >> 8) & 0xff;
		this.bSW = color & 0xff;
	}

	public int getColorSW() {
		return this.colorSW;
	}

	public void setColorSE(int color) {
		this.colorSE = color;
		this.rSE = (color >> 16) & 0xff;
		this.gSE = (color >> 8) & 0xff;
		this.bSE = color & 0xff;
	}

	public int getColorSE() {
		return this.colorSE;
	}

	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		super.setDimensions(width, height);
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		float fx = (float) x / this.width;
		float fy = (float) y / this.height;
		float p;
		float q;

		p = this.rNW + (this.rNE - this.rNW) * fx;
		q = this.rSW + (this.rSE - this.rSW) * fx;
		int r = (int) (p + (q - p) * fy + 0.5f);

		p = this.gNW + (this.gNE - this.gNW) * fx;
		q = this.gSW + (this.gSE - this.gSW) * fx;
		int g = (int) (p + (q - p) * fy + 0.5f);

		p = this.bNW + (this.bNE - this.bNW) * fx;
		q = this.bSW + (this.bSE - this.bSW) * fx;
		int b = (int) (p + (q - p) * fy + 0.5f);

		return 0xff000000 | (r << 16) | (g << 8) | b;
	}

	@Override
	public String toString() {
		return "Texture/Four Color Fill...";
	}
}
