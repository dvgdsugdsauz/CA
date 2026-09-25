package com.ca.charteredAccountant.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

public final class CommonUtil {

	private CommonUtil() {
	}

	/** Trims the value and returns null for blank input, so optional fields store NULL, not "". */
	public static String trimToNull(String value) {
		return StringUtils.hasText(value) ? value.trim() : null;
	}

	/** Lower-case email with surrounding spaces removed. */
	public static String normalizeEmail(String email) {
		String trimmed = trimToNull(email);
		return trimmed == null ? null : trimmed.toLowerCase();
	}

	/** "Gachibowli, Madhapur" -> ["Gachibowli", "Madhapur"]. */
	public static List<String> splitCsv(String csv) {
		if (!StringUtils.hasText(csv)) {
			return List.of();
		}
		return Arrays.stream(csv.split(",")).map(String::trim).filter(StringUtils::hasText).toList();
	}

	/** ["Gachibowli", "Madhapur"] -> "Gachibowli,Madhapur". */
	public static String joinCsv(List<String> values) {
		if (values == null || values.isEmpty()) {
			return null;
		}
		return String.join(",", values.stream().map(String::trim).filter(StringUtils::hasText).toList());
	}

	/** Turns a title into a URL slug: "GST Registration & Returns" -> "gst-registration-returns". */
	public static String toSlug(String text) {
		if (!StringUtils.hasText(text)) {
			return null;
		}
		String slug = text.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-+|-+$)", "");
		return slug.isEmpty() ? null : slug;
	}

	/** Explicit slug if given, otherwise one derived from the title. */
	public static String resolveSlug(String slug, String title) {
		String explicit = trimToNull(slug);
		return explicit != null ? toSlug(explicit) : toSlug(title);
	}

	public static int orZero(Integer value) {
		return value == null ? 0 : value;
	}

}
