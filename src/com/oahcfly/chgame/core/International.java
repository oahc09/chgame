package com.oahcfly.chgame.core;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.badlogic.gdx.utils.StreamUtils;

/**
 * A {@code I18NBundle} provides {@code Locale}-specific resources loaded from
 * property files. A bundle contains a number of named resources, whose names
 * and values are {@code Strings}. A bundle may have a parent bundle, and when a
 * resource is not found in a bundle, the parent bundle is searched for the
 * resource. If the fallback mechanism reaches the base bundle and still can't
 * find the resource it throws a {@code MissingResourceException}.
 * 
 * <ul>
 * <li>All bundles for the same group of resources share a common base bundle.
 * This base bundle acts as the root and is the last fallback in case none of
 * its children was able to respond to a request.</li>
 * <li>The first level contains changes between different languages. Only the
 * differences between a language and the language of the base bundle need to be
 * handled by a language-specific {@code I18NBundle}.</li>
 * <li>The second level contains changes between different countries that use
 * the same language. Only the differences between a country and the country of
 * the language bundle need to be handled by a country-specific
 * {@code I18NBundle}.</li>
 * <li>The third level contains changes that don't have a geographic reason
 * (e.g. changes that where made at some point in time like {@code PREEURO}
 * where the currency of come countries changed. The country bundle would return
 * the current currency (Euro) and the {@code PREEURO} variant bundle would
 * return the old currency (e.g. DM for Germany).</li>
 * </ul>
 * 
 * <strong>Examples</strong>
 * <ul>
 * <li>BaseName (base bundle)
 * <li>BaseName_de (german language bundle)
 * <li>BaseName_fr (french language bundle)
 * <li>BaseName_de_DE (bundle with Germany specific resources in german)
 * <li>BaseName_de_CH (bundle with Switzerland specific resources in german)
 * <li>BaseName_fr_CH (bundle with Switzerland specific resources in french)
 * <li>BaseName_de_DE_PREEURO (bundle with Germany specific resources in german
 * of the time before the Euro)
 * <li>BaseName_fr_FR_PREEURO (bundle with France specific resources in french
 * of the time before the Euro)
 * </ul>
 * 
 * It's also possible to create variants for languages or countries. This can be
 * done by just skipping the country or language abbreviation:
 * BaseName_us__POSIX or BaseName__DE_PREEURO. But it's not allowed to
 * circumvent both language and country: BaseName___VARIANT is illegal.
 * 
 * @see PropertiesUtils
 * 
 * @author davebaol
 */

public class International {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static boolean simpleFormatter = false;

	/**
	 * The parent of this {@code I18NBundle} that is used if this bundle doesn't
	 * include the requested resource.
	 */
	private International parent;

	/** The locale for this bundle. */
	private Locale locale;

	/** The properties for this bundle. */
	private ObjectMap<String, String> properties;

	/** The formatter used for argument replacement. */
	private TextFormatter formatter;

	/**
	 * Returns the flag indicating whether to use the simplified message pattern
	 * syntax (default is false). This flag is always assumed to be true on GWT
	 * backend.
	 */
	public static boolean getSimpleFormatter() {
		return simpleFormatter;
	}

	/**
	 * Sets the flag indicating whether to use the simplified message pattern.
	 * The flag must be set before calling the factory methods
	 * {@code createBundle}. Notice that this method has no effect on the GWT
	 * backend where it's always assumed to be true.
	 */
	public static void setSimpleFormatter(boolean enabled) {
		simpleFormatter = enabled;
	}

	/**
	 * Creates a new bundle using the specified <code>baseFileHandle</code>, the
	 * default locale and the default encoding "UTF-8".
	 * 
	 * @param baseFileHandle
	 *            the file handle to the base of the bundle
	 * @exception NullPointerException
	 *                if <code>baseFileHandle</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no bundle for the specified base file handle can be
	 *                found
	 * @return a bundle for the given base file handle and the default locale
	 */
	public static International createBundle(FileHandle baseFileHandle) {
		return createBundleImpl(baseFileHandle, Locale.getDefault(),
				DEFAULT_ENCODING);
	}

	/**
	 * Creates a new bundle using the specified <code>baseFileHandle</code> and
	 * <code>locale</code>; the default encoding "UTF-8" is used.
	 * 
	 * @param baseFileHandle
	 *            the file handle to the base of the bundle
	 * @param locale
	 *            the locale for which a bundle is desired
	 * @return a bundle for the given base file handle and locale
	 * @exception NullPointerException
	 *                if <code>baseFileHandle</code> or <code>locale</code> is
	 *                <code>null</code>
	 * @exception MissingResourceException
	 *                if no bundle for the specified base file handle can be
	 *                found
	 */
	public static International createBundle(FileHandle baseFileHandle,
			Locale locale) {
		return createBundleImpl(baseFileHandle, locale, DEFAULT_ENCODING);
	}

	/**
	 * Creates a new bundle using the specified <code>baseFileHandle</code> and
	 * <code>encoding</code>; the default locale is used.
	 * 
	 * @param baseFileHandle
	 *            the file handle to the base of the bundle
	 * @param encoding
	 *            the charter encoding
	 * @return a bundle for the given base file handle and locale
	 * @exception NullPointerException
	 *                if <code>baseFileHandle</code> or <code>encoding</code> is
	 *                <code>null</code>
	 * @exception MissingResourceException
	 *                if no bundle for the specified base file handle can be
	 *                found
	 */
	public static International createBundle(FileHandle baseFileHandle,
			String encoding) {
		return createBundleImpl(baseFileHandle, Locale.getDefault(), encoding);
	}

	/**
	 * Creates a new bundle using the specified <code>baseFileHandle</code>,
	 * <code>locale</code> and <code>encoding</code>.
	 * 
	 * @param baseFileHandle
	 *            the file handle to the base of the bundle
	 * @param locale
	 *            the locale for which a bundle is desired
	 * @param encoding
	 *            the charter encoding
	 * @return a bundle for the given base file handle and locale
	 * @exception NullPointerException
	 *                if <code>baseFileHandle</code>, <code>locale</code> or
	 *                <code>encoding</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no bundle for the specified base file handle can be
	 *                found
	 */
	public static International createBundle(FileHandle baseFileHandle,
			Locale locale, String encoding) {
		return createBundleImpl(baseFileHandle, locale, encoding);
	}

	private static International createBundleImpl(FileHandle baseFileHandle,
			Locale locale, String encoding) {
		if (baseFileHandle == null || locale == null || encoding == null)
			throw new NullPointerException();

		International bundle = null;
		International baseBundle = null;
		Locale targetLocale = locale;
		do {
			// Create the candidate locales
			List<Locale> candidateLocales = getCandidateLocales(targetLocale);

			// Load the bundle and its parents recursively
			bundle = loadBundleChain(baseFileHandle, encoding,
					candidateLocales, 0, baseBundle);

			// Check the loaded bundle (if any)
			if (bundle != null) {
				Locale bundleLocale = bundle.getLocale(); // WTH? GWT can't
															// access
															// bundle.locale
															// directly
				boolean isBaseBundle = bundleLocale.equals(Locale.ROOT);

				if (!isBaseBundle || bundleLocale.equals(locale)) {
					// Found the bundle for the requested locale
					break;
				}
				if (candidateLocales.size() == 1
						&& bundleLocale.equals(candidateLocales.get(0))) {
					// Found the bundle for the only candidate locale
					break;
				}
				if (isBaseBundle && baseBundle == null) {
					// Store the base bundle and keep on processing the
					// remaining fallback locales
					baseBundle = bundle;
				}
			}

			// Set next fallback locale
			targetLocale = getFallbackLocale(targetLocale);

		} while (targetLocale != null);

		if (bundle == null) {
			if (baseBundle == null) {
				// No bundle found
				throw new MissingResourceException(
						"Can't find bundle for base file handle "
								+ baseFileHandle.path() + ", locale " + locale,
						baseFileHandle + "_" + locale, "");
			}
			// Set the base bundle to be returned
			bundle = baseBundle;
		}

		return bundle;
	}

	/**
	 * Returns a <code>List</code> of <code>Locale</code>s as candidate locales
	 * for the given <code>locale</code>. This method is called by the
	 * <code>createBundle</code> factory method each time the factory method
	 * tries finding a resource bundle for a target <code>Locale</code>.
	 * 
	 * <p>
	 * The sequence of the candidate locales also corresponds to the runtime
	 * resource lookup path (also known as the <I>parent chain</I>), if the
	 * corresponding resource bundles for the candidate locales exist and their
	 * parents are not defined by loaded resource bundles themselves. The last
	 * element of the list is always the {@linkplain Locale#ROOT root locale},
	 * meaning that the base bundle is the terminal of the parent chain.
	 * 
	 * <p>
	 * If the given locale is equal to <code>Locale.ROOT</code> (the root
	 * locale), a <code>List</code> containing only the root <code>Locale</code>
	 * is returned. In this case, the <code>createBundle</code> factory method
	 * loads only the base bundle as the resulting resource bundle.
	 * 
	 * <p>
	 * This implementation returns a <code>List</code> containing
	 * <code>Locale</code>s in the following sequence:
	 * 
	 * <pre>
	 *     Locale(language, country, variant)
	 *     Locale(language, country)
	 *     Locale(language)
	 *     Locale.ROOT
	 * </pre>
	 * 
	 * where <code>language</code>, <code>country</code> and
	 * <code>variant</code> are the language, country and variant values of the
	 * given <code>locale</code>, respectively. Locales where the final
	 * component values are empty strings are omitted.
	 * 
	 * <p>
	 * For example, if the given base name is "Messages" and the given
	 * <code>locale</code> is <code>Locale("ja",&nbsp;"",&nbsp;"XX")</code>,
	 * then a <code>List</code> of <code>Locale</code>s:
	 * 
	 * <pre>
	 *     Locale("ja", "", "XX")
	 *     Locale("ja")
	 *     Locale.ROOT
	 * </pre>
	 * 
	 * is returned. And if the resource bundles for the "ja" and ""
	 * <code>Locale</code>s are found, then the runtime resource lookup path
	 * (parent chain) is:
	 * 
	 * <pre>
	 *     Messages_ja -> Messages
	 * </pre>
	 * 
	 * @param locale
	 *            the locale for which a resource bundle is desired
	 * @return a <code>List</code> of candidate <code>Locale</code>s for the
	 *         given <code>locale</code>
	 * @exception NullPointerException
	 *                if <code>locale</code> is <code>null</code>
	 */
	private static List<Locale> getCandidateLocales(Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		List<Locale> locales = new ArrayList<Locale>(4);
		if (variant.length() > 0) {
			locales.add(locale);
		}
		if (country.length() > 0) {
			locales.add((locales.size() == 0) ? locale : new Locale(language,
					country));
		}
		if (language.length() > 0) {
			locales.add((locales.size() == 0) ? locale : new Locale(language));
		}
		locales.add(Locale.ROOT);
		return locales;
	}

	/**
	 * Returns a <code>Locale</code> to be used as a fallback locale for further
	 * bundle searches by the <code>createBundle</code> factory method. This
	 * method is called from the factory method every time when no resulting
	 * bundle has been found for <code>baseFileHandler</code> and
	 * <code>locale</code>, where locale is either the parameter for
	 * <code>createBundle</code> or the previous fallback locale returned by
	 * this method.
	 * 
	 * <p>
	 * This method returns the {@linkplain Locale#getDefault() default
	 * <code>Locale</code>} if the given <code>locale</code> isn't the default
	 * one. Otherwise, <code>null</code> is returned.
	 * 
	 * @param locale
	 *            the <code>Locale</code> for which <code>createBundle</code>
	 *            has been unable to find any resource bundles (except for the
	 *            base bundle)
	 * @return a <code>Locale</code> for the fallback search, or
	 *         <code>null</code> if no further fallback search is needed.
	 * @exception NullPointerException
	 *                if <code>locale</code> is <code>null</code>
	 */
	private static Locale getFallbackLocale(Locale locale) {
		Locale defaultLocale = Locale.getDefault();
		return locale.equals(defaultLocale) ? null : defaultLocale;
	}

	private static International loadBundleChain(FileHandle baseFileHandle,
			String encoding, List<Locale> candidateLocales, int candidateIndex,
			International baseBundle) {
		Locale targetLocale = candidateLocales.get(candidateIndex);
		International parent = null;
		if (candidateIndex != candidateLocales.size() - 1) {
			// Load recursively the parent having the next candidate locale
			parent = loadBundleChain(baseFileHandle, encoding,
					candidateLocales, candidateIndex + 1, baseBundle);
		} else if (baseBundle != null && targetLocale.equals(Locale.ROOT)) {
			return baseBundle;
		}

		// Load the bundle
		International bundle = loadBundle(baseFileHandle, encoding,
				targetLocale);
		if (bundle != null) {
			bundle.parent = parent;
			return bundle;
		}

		return parent;
	}

	// Tries to load the bundle for the given locale.
	private static International loadBundle(FileHandle baseFileHandle,
			String encoding, Locale targetLocale) {
		International bundle = null;
		InputStream stream = null;
		try {
			FileHandle fileHandle = toFileHandle(baseFileHandle, targetLocale);
			if (fileHandle.exists()) {
				// Instantiate the bundle
				bundle = new International();

				// Load bundle properties from the stream with the specified
				// encoding
				stream = fileHandle.read();
				bundle.load(new InputStreamReader(stream, encoding));
			}
		} catch (Throwable t) {
			throw new GdxRuntimeException(t);
		} finally {
			StreamUtils.closeQuietly(stream);
		}
		if (bundle != null) {
			bundle.setLocale(targetLocale);
		}

		return bundle;
	}

	/**
	 * Load the properties from the specified reader.
	 * 
	 * @param reader
	 *            the reader
	 * @throws IOException
	 *             if an error occurred when reading from the input stream.
	 */
	// NOTE:
	// This method can't be private otherwise GWT can't access it from
	// loadBundle()
	protected void load(Reader reader) throws IOException {
		properties = new ObjectMap<String, String>();
		PropertiesUtils.load(properties, reader);
	}

	/**
	 * Converts the given <code>baseFileHandle</code> and <code>locale</code> to
	 * the corresponding file handle.
	 * 
	 * <p>
	 * This implementation returns the <code>baseFileHandle</code>'s sibling
	 * with following value:
	 * 
	 * <pre>
	 * baseFileHandle.name() + &quot;_&quot; + language + &quot;_&quot; + country + &quot;_&quot; + variant
	 * 		+ &quot;.properties&quot;
	 * </pre>
	 * 
	 * where <code>language</code>, <code>country</code> and
	 * <code>variant</code> are the language, country and variant values of
	 * <code>locale</code>, respectively. Final component values that are empty
	 * Strings are omitted along with the preceding '_'. If all of the values
	 * are empty strings, then <code>baseFileHandle.name()</code> is returned
	 * with ".properties" appended.
	 * 
	 * @param baseFileHandle
	 *            the file handle to the base of the bundle
	 * @param locale
	 *            the locale for which a resource bundle should be loaded
	 * @return the file handle for the bundle
	 * @exception NullPointerException
	 *                if <code>baseFileHandle</code> or <code>locale</code> is
	 *                <code>null</code>
	 */
	private static FileHandle toFileHandle(FileHandle baseFileHandle,
			Locale locale) {
		StringBuilder sb = new StringBuilder(baseFileHandle.name());
		if (!locale.equals(Locale.ROOT)) {
			String language = locale.getLanguage();
			String country = locale.getCountry();
			String variant = locale.getVariant();
			boolean emptyLanguage = "".equals(language);
			boolean emptyCountry = "".equals(country);
			boolean emptyVariant = "".equals(variant);

			if (!(emptyLanguage && emptyCountry && emptyVariant)) {
				sb.append('_');
				if (!emptyVariant) {
					sb.append(language).append('_').append(country).append('_')
							.append(variant);
				} else if (!emptyCountry) {
					sb.append(language).append('_').append(country);
				} else {
					sb.append(language);
				}
			}
		}
		return baseFileHandle.sibling(sb.append(".properties").toString());
	}

	/**
	 * Returns the locale of this bundle. This method can be used after a call
	 * to <code>createBundle()</code> to determine whether the resource bundle
	 * returned really corresponds to the requested locale or is a fallback.
	 * 
	 * @return the locale of this bundle
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the bundle locale. This method is private because a bundle can't
	 * change the locale during its life.
	 * 
	 * @param locale
	 */
	private void setLocale(Locale locale) {
		this.locale = locale;
		this.formatter = new TextFormatter(locale, !simpleFormatter);
	}

	/**
	 * Gets a string for the given key from this bundle or one of its parents.
	 * 
	 * @param key
	 *            the key for the desired string
	 * @exception NullPointerException
	 *                if <code>key</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no string for the given key can be found
	 * @return the string for the given key
	 */
	public final String get(String key) {
		String result = properties.get(key);
		if (result == null) {
			if (parent != null)
				result = parent.get(key);
			if (result == null)
				throw new MissingResourceException("Can't find bundle key "
						+ key, this.getClass().getName(), key);
		}
		return result;
	}

	/**
	 * Gets the string with the specified key from this bundle or one of its
	 * parent after replacing the given arguments if they occur.
	 * 
	 * @param key
	 *            the key for the desired string
	 * @param args
	 *            the arguments to be replaced in the string associated to the
	 *            given key.
	 * @exception NullPointerException
	 *                if <code>key</code> is <code>null</code>
	 * @exception MissingResourceException
	 *                if no string for the given key can be found
	 * @return the string for the given key formatted with the given arguments
	 */
	public String format(String key, Object... args) {
		return formatter.format(get(key), args);
	}

	/**
	 * var3d????????????,??????????????????(??????????????????)
	 */
	public String getVaulesString() {
		HashMap<String, String> all = new HashMap<String, String>();
		for (String string : properties.values()) {
			string = string.replaceAll("(?s)(.)(?=.*\\1)", "");// ??????????????????
			for (int i = 0; i < string.length(); i++) {
				String one = string.substring(i, i + 1);
				if (all.get(one) == null) {
					all.put(one, one);
				}
			}
		}
		String out = "";
		for (String string : all.keySet()) {
			out += string;
		}
		return out;
	}

	class TextFormatter {

		private MessageFormat messageFormat;
		private StringBuilder buffer;

		public TextFormatter(Locale locale, boolean useMessageFormat) {
			buffer = new StringBuilder();
			if (useMessageFormat)
				messageFormat = new MessageFormat("", locale);
		}

		/**
		 * Formats the given {@code pattern} replacing its placeholders with the
		 * actual arguments specified by {@code args}.
		 * <p>
		 * If this {@code TextFormatter} has been instantiated with
		 * {@link #TextFormatter(Locale, boolean) TextFormatter(locale, true)}
		 * {@link MessageFormat} is used to process the pattern, meaning that
		 * the actual arguments are properly localized with the locale of this
		 * {@code TextFormatter}.
		 * <p>
		 * On the contrary, if this {@code TextFormatter} has been instantiated
		 * with {@link #TextFormatter(Locale, boolean) TextFormatter(locale,
		 * false)} pattern's placeholders are expected to be in the simplified
		 * form {0}, {1}, {2} and so on and they will be replaced with the
		 * corresponding object from {@code args} converted to a string with
		 * {@code toString()}, so without taking into account the locale.
		 * <p>
		 * In both cases, there's only one simple escaping rule, i.e. a left
		 * curly bracket must be doubled if you want it to be part of your
		 * string.
		 * <p>
		 * It's worth noting that the rules for using single quotes within
		 * {@link MessageFormat} patterns have shown to be somewhat confusing.
		 * In particular, it isn't always obvious to localizers whether single
		 * quotes need to be doubled or not. For this very reason we decided to
		 * offer the simpler escaping rule above without limiting the expressive
		 * power of message format patterns. So, if you're used to
		 * MessageFormat's syntax, remember that with {@code TextFormatter}
		 * single quotes never need to be escaped!
		 * 
		 * @param pattern
		 *            the pattern
		 * @param args
		 *            the arguments
		 * @return the formatted pattern
		 * @exception IllegalArgumentException
		 *                if the pattern is invalid
		 */
		public String format(String pattern, Object... args) {
			if (messageFormat != null) {
				messageFormat.applyPattern(replaceEscapeChars(pattern));
				return messageFormat.format(args);
			}
			return simpleFormat(pattern, args);
		}

		// This code is needed because a simple replacement like
		// pattern.replace("'", "''").replace("{{", "'{'");
		// can't properly manage some special cases.
		// For example, the expected output for {{{{ is {{ but you get {'{
		// instead.
		// Also this code is optimized since a new string is returned only if
		// something has been replaced.
		private String replaceEscapeChars(String pattern) {
			buffer.setLength(0);
			boolean changed = false;
			int len = pattern.length();
			for (int i = 0; i < len; i++) {
				char ch = pattern.charAt(i);
				if (ch == '\'') {
					changed = true;
					buffer.append("''");
				} else if (ch == '{') {
					int j = i + 1;
					while (j < len && pattern.charAt(j) == '{')
						j++;
					int escaped = (j - i) / 2;
					if (escaped > 0) {
						changed = true;
						buffer.append('\'');
						do {
							buffer.append('{');
						} while ((--escaped) > 0);
						buffer.append('\'');
					}
					if ((j - i) % 2 != 0)
						buffer.append('{');
					i = j - 1;
				} else {
					buffer.append(ch);
				}
			}
			return changed ? buffer.toString() : pattern;
		}

		/**
		 * Formats the given {@code pattern} replacing any placeholder of the
		 * form {0}, {1}, {2} and so on with the corresponding object from
		 * {@code args} converted to a string with {@code toString()}, so
		 * without taking into account the locale.
		 * <p>
		 * This method only implements a small subset of the grammar supported
		 * by {@link java.text.MessageFormat}. Especially, placeholder are only
		 * made up of an index; neither the type nor the style are supported.
		 * <p>
		 * If nothing has been replaced this implementation returns the pattern
		 * itself.
		 * 
		 * @param pattern
		 *            the pattern
		 * @param args
		 *            the arguments
		 * @return the formatted pattern
		 * @exception IllegalArgumentException
		 *                if the pattern is invalid
		 */
		private String simpleFormat(String pattern, Object... args) {
			buffer.setLength(0);
			boolean changed = false;
			int placeholder = -1;
			int patternLength = pattern.length();
			for (int i = 0; i < patternLength; ++i) {
				char ch = pattern.charAt(i);
				if (placeholder < 0) { // processing constant part
					if (ch == '{') {
						changed = true;
						if (i + 1 < patternLength
								&& pattern.charAt(i + 1) == '{') {
							buffer.append(ch); // handle escaped '{'
							++i;
						} else {
							placeholder = 0; // switch to placeholder part
						}
					} else {
						buffer.append(ch);
					}
				} else { // processing placeholder part
					if (ch == '}') {
						if (placeholder >= args.length)
							throw new IllegalArgumentException(
									"Argument index out of bounds: "
											+ placeholder);
						if (pattern.charAt(i - 1) == '{')
							throw new IllegalArgumentException(
									"Missing argument index after a left curly brace");
						if (args[placeholder] == null)
							buffer.append("null"); // append null argument
						else
							buffer.append(args[placeholder].toString()); // append
																			// actual
																			// argument
						placeholder = -1; // switch to constant part
					} else {
						if (ch < '0' || ch > '9')
							throw new IllegalArgumentException("Unexpected '"
									+ ch + "' while parsing argument index");
						placeholder = placeholder * 10 + (ch - '0');
					}
				}
			}
			if (placeholder >= 0)
				throw new IllegalArgumentException(
						"Unmatched braces in the pattern.");

			return changed ? buffer.toString() : pattern;
		}
	}
}
