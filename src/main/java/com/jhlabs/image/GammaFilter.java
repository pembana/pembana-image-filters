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
 * A filter for changing the gamma of an image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class GammaFilter extends TransferFilter {

	private float rGamma;
	private float gGamma;
	private float bGamma;

	public GammaFilter() {
		this(1.0f);
	}

	public GammaFilter(float gamma) {
		this(gamma, gamma, gamma);
	}

	public GammaFilter(float rGamma, float gGamma, float bGamma) {
		setGamma(rGamma, gGamma, bGamma);
	}

	public void setGamma(float rGamma, float gGamma, float bGamma) {
		this.rGamma = rGamma;
		this.gGamma = gGamma;
		this.bGamma = bGamma;
		this.initialized = false;
	}

	public void setGamma(float gamma) {
		setGamma(gamma, gamma, gamma);
	}

	public float getGamma() {
		return this.rGamma;
	}

	@Override
	protected void initialize() {
		this.rTable = makeTable(this.rGamma);

		if (this.gGamma == this.rGamma) {
			this.gTable = this.rTable;
		}
		else {
			this.gTable = makeTable(this.gGamma);
		}

		if (this.bGamma == this.rGamma) {
			this.bTable = this.rTable;
		}
		else if (this.bGamma == this.gGamma) {
			this.bTable = this.gTable;
		}
		else {
			this.bTable = makeTable(this.bGamma);
		}
	}

	protected int[] makeTable(float gamma) {
		int[] table = new int[256];
		for (int i = 0; i < 256; i++) {
			int v = (int) ((255.0 * Math.pow(i / 255.0, 1.0 / gamma)) + 0.5);
			if (v > 255) {
				v = 255;
			}
			table[i] = v;
		}
		return table;
	}

	@Override
	public String toString() {
		return "Colors/Gamma...";
	}

}

