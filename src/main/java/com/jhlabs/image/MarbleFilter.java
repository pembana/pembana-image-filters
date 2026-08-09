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

import java.awt.Rectangle;

import com.jhlabs.math.Noise;

/**
 * This filter applies a marbling effect to an image, displacing pixels by random amounts.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class MarbleFilter extends TransformFilter {

	private float[] sinTable;
	private float[] cosTable;
	private float xScale = 4;
	private float yScale = 4;
	private float amount = 1;
	private float turbulence = 1;

	public MarbleFilter() {
		setEdgeAction(CLAMP);
	}

	public void setXScale(float xScale) {
		this.xScale = xScale;
	}

	public float getXScale() {
		return this.xScale;
	}

	public void setYScale(float yScale) {
		this.yScale = yScale;
	}

	public float getYScale() {
		return this.yScale;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setTurbulence(float turbulence) {
		this.turbulence = turbulence;
	}

	public float getTurbulence() {
		return this.turbulence;
	}

	private void initialize() {
		this.sinTable = new float[256];
		this.cosTable = new float[256];
		for (int i = 0; i < 256; i++) {
			float angle = ImageMath.TWO_PI * i / 256f * this.turbulence;
			this.sinTable[i] = (float) (-this.yScale * Math.sin(angle));
			this.cosTable[i] = (float) (this.yScale * Math.cos(angle));
		}
	}

	private int displacementMap(int x, int y) {
		return PixelUtils.clamp((int) (127 * (1 + Noise.noise2(x / this.xScale, y / this.xScale))));
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		int displacement = displacementMap(x, y);
		out[0] = x + this.sinTable[displacement];
		out[1] = y + this.cosTable[displacement];
	}

	@Override
	protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
		initialize();
		return super.filterPixels(width, height, inPixels, transformedSpace);
	}

	@Override
	public String toString() {
		return "Distort/Marble...";
	}
}
