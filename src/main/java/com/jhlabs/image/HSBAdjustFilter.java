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

import java.awt.Color;

/**
 * A filter which adjusts the hue, saturation and brightness of an image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class HSBAdjustFilter extends PointFilter {

	private float hFactor;
	private float sFactor;
	private float bFactor;
	private final float[] hsb = new float[3];

	public HSBAdjustFilter() {
		this(0, 0, 0);
	}

	public HSBAdjustFilter(float r, float g, float b) {
		this.hFactor = r;
		this.sFactor = g;
		this.bFactor = b;
		this.canFilterIndexColorModel = true;
	}

	public void setHFactor(float hFactor) {
		this.hFactor = hFactor;
	}

	public float getHFactor() {
		return this.hFactor;
	}

	public void setSFactor(float sFactor) {
		this.sFactor = sFactor;
	}

	public float getSFactor() {
		return this.sFactor;
	}

	public void setBFactor(float bFactor) {
		this.bFactor = bFactor;
	}

	public float getBFactor() {
		return this.bFactor;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		Color.RGBtoHSB(r, g, b, this.hsb);
		this.hsb[0] += this.hFactor;
		while (this.hsb[0] < 0) {
			this.hsb[0] += Math.PI * 2;
		}
		this.hsb[1] += this.sFactor;
		if (this.hsb[1] < 0) {
			this.hsb[1] = 0;
		}
		else if (this.hsb[1] > 1.0) {
			this.hsb[1] = 1.0f;
		}
		this.hsb[2] += this.bFactor;
		if (this.hsb[2] < 0) {
			this.hsb[2] = 0;
		}
		else if (this.hsb[2] > 1.0) {
			this.hsb[2] = 1.0f;
		}
		rgb = Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
		return a | (rgb & 0xffffff);
	}

	@Override
	public String toString() {
		return "Colors/Adjust HSB...";
	}
}

