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
 * A filter which adjusts the contrast of an image.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class ContrastFilter extends TransferFilter {

	private float brightness = 1.0f;
	private float contrast = 0.5f;

	@Override
	protected float transferFunction(float f) {
		f = f * this.brightness;
		f = (f - 0.5f) * this.contrast + 0.5f;
		return f;
	}

	public void setBrightness(float brightness) {
		this.brightness = brightness;
		this.initialized = false;
	}

	public float getBrightness() {
		return this.brightness;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
		this.initialized = false;
	}

	public float getContrast() {
		return this.contrast;
	}

	@Override
	public String toString() {
		return "Colors/Contrast...";
	}

}

