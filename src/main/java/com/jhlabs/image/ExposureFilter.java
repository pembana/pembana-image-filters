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
 * The {@code ExposureFilter} class adjusts the exposure levels of an image.
 * It modifies the brightness of the image by applying an exponential
 * transfer function to the pixel values, based on a user-specified exposure value.
 * <p>
 * This filter is a subclass of the {@code TransferFilter} class and specifically
 * overrides the transfer function behavior to achieve its effect.
 * <p>
 * The exposure level can be configured using the {@code setExposure} method,
 * and the current exposure value can be retrieved using the {@code getExposure} method.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ExposureFilter extends TransferFilter {

	private float exposure = 1.0f;

	@Override
	protected float transferFunction(float f) {
		return 1 - (float) Math.exp(-f * this.exposure);
	}

	public void setExposure(float exposure) {
		this.exposure = exposure;
		this.initialized = false;
	}

	public float getExposure() {
		return this.exposure;
	}

	@Override
	public String toString() {
		return "Colors/Exposure...";
	}

}

