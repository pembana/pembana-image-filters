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
 * The GainFilter class is a specialized implementation of the {@link TransferFilter} that
 * modifies the intensity distribution of an image through a gain and bias adjustment.
 * The gain factor is used to control the contrast of midtones in the image, while the
 * bias factor shifts the intensity values to control brightness.
 * <p>
 * The filter works by applying a transfer function to each pixel in the image, where
 * the gain and bias are applied sequentially to adjust the intensity levels. This
 * allows for fine control over the image's tone curve.
 * <p>
 * Methods are provided to set and get the gain and bias values, and the filter regenerates
 * its internal lookup tables whenever either value is modified.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class GainFilter extends TransferFilter {

	private float gain = 0.5f;
	private float bias = 0.5f;

	@Override
	protected float transferFunction(float f) {
		f = ImageMath.gain(f, this.gain);
		f = ImageMath.bias(f, this.bias);
		return f;
	}

	public void setGain(float gain) {
		this.gain = gain;
		this.initialized = false;
	}

	public float getGain() {
		return this.gain;
	}

	public void setBias(float bias) {
		this.bias = bias;
		this.initialized = false;
	}

	public float getBias() {
		return this.bias;
	}

	@Override
	public String toString() {
		return "Colors/Gain...";
	}

}

