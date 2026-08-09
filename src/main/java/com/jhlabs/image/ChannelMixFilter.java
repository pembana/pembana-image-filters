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
 * A filter which allows the red, grren and blue channels of an image to be mixed into each other.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ChannelMixFilter extends PointFilter {

	private int blueGreen;
	private int redBlue;
	private int greenRed;
	private int intoR;
	private int intoG;
	private int intoB;

	public ChannelMixFilter() {
		this.canFilterIndexColorModel = true;
	}

	public void setBlueGreen(int blueGreen) {
		this.blueGreen = blueGreen;
	}

	public int getBlueGreen() {
		return this.blueGreen;
	}

	public void setRedBlue(int redBlue) {
		this.redBlue = redBlue;
	}

	public int getRedBlue() {
		return this.redBlue;
	}

	public void setGreenRed(int greenRed) {
		this.greenRed = greenRed;
	}

	public int getGreenRed() {
		return this.greenRed;
	}

	public void setIntoR(int intoR) {
		this.intoR = intoR;
	}

	public int getIntoR() {
		return this.intoR;
	}

	public void setIntoG(int intoG) {
		this.intoG = intoG;
	}

	public int getIntoG() {
		return this.intoG;
	}

	public void setIntoB(int intoB) {
		this.intoB = intoB;
	}

	public int getIntoB() {
		return this.intoB;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		int nr = PixelUtils.clamp((this.intoR * (this.blueGreen * g + (255 - this.blueGreen) * b) / 255 + (255 - this.intoR) * r) / 255);
		int ng = PixelUtils.clamp((this.intoG * (this.redBlue * b + (255 - this.redBlue) * r) / 255 + (255 - this.intoG) * g) / 255);
		int nb = PixelUtils.clamp((this.intoB * (this.greenRed * r + (255 - this.greenRed) * g) / 255 + (255 - this.intoB) * b) / 255);
		return a | (nr << 16) | (ng << 8) | nb;
	}

	@Override
	public String toString() {
		return "Colors/Mix Channels...";
	}
}

