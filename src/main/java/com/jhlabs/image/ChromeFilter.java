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

import java.awt.image.BufferedImage;

/**
 * A filter which simulates chrome.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ChromeFilter extends LightFilter {
	private float amount = 0.5f;
	private float exposure = 1.0f;

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setExposure(float exposure) {
		this.exposure = exposure;
	}

	public float getExposure() {
		return this.exposure;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		setColorSource(LightFilter.COLORS_CONSTANT);
		dst = super.filter(src, dst);
		TransferFilter tf = new TransferFilter() {
			@Override
			protected float transferFunction(float v) {
				v += ChromeFilter.this.amount * (float) Math.sin(v * 2 * Math.PI);
				return 1 - (float) Math.exp(-v * ChromeFilter.this.exposure);
			}
		};
		return tf.filter(dst, dst);
	}

	@Override
	public String toString() {
		return "Effects/Chrome...";
	}
}

