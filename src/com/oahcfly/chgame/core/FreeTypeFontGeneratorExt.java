package com.oahcfly.chgame.core;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphSlot;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Library;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author 此项目类由libgdx1群 群友 馒头虫 创建，Var3D在馒头虫的工作基础上仅解决了文本错位等问题
 * 
 *         hx 修改至libgdx-freetype项目
 * 
 *         修改为读取系统配置的ttf文件
 * 
 *         如要使用第三方ttf 请使用原生项目
 * 
 *         Generates {@link BitmapFont} and {@link BitmapFontData} instances
 *         from TrueType font files.</p>
 * 
 *         Usage example:
 * 
 *         <pre>
 * FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
 * 		Gdx.files.internal(&quot;myfont.ttf&quot;));
 * BitmapFont font = gen.generateFont(16);
 * gen.dispose();
 * </pre>
 * 
 *         The generator has to be disposed once it is no longer used. The
 *         returned {@link BitmapFont} instances are managed by the user and
 *         have to be disposed as usual.
 * 
 * 
 */
public class FreeTypeFontGeneratorExt implements Disposable {
	public static final String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*";
	final Library library;
	Face face;
	String filePath;
	boolean bitmapped = false;
	FreeTypeBitmapFontData data = null;
	FreeTypeFontParameter par = null;
	TtfFamily ttfs;
	int size = 20;
	public static Pixmap pxmap = null;
	public static Texture t = null;
	/**
	 * The maximum texture size allowed by generateData, when storing in a
	 * texture atlas. Multiple texture pages will be created if necessary.
	 */
	private static int maxTextureSize = 1024;

	/**
	 * A hint to scale the texture as needed, without capping it at any maximum
	 * size
	 */
	public static final int NO_MAXIMUM = -1;

	/**
	 * Sets the maximum size that will be used when generating texture atlases
	 * for glyphs with <tt>generateData()</tt>. The default is 1024. By
	 * specifying NO_MAXIMUM, the texture atlas will scale as needed.
	 * 
	 * The power-of-two square texture size will be capped to the given
	 * <tt>texSize</tt>. It's recommended that a power-of-two value be used
	 * here.
	 * 
	 * Multiple pages may be used to fit all the generated glyphs. You can query
	 * the resulting number of pages by calling
	 * <tt>bitmapFont.getRegions().length</tt> or
	 * <tt>freeTypeBitmapFontData.getTextureRegions().length</tt>.
	 * 
	 * If PixmapPacker is specified when calling generateData, this parameter is
	 * ignored.
	 * 
	 * @param texSize
	 *            the maximum texture size for one page of glyphs
	 */
	public static void setMaxTextureSize(int texSize) {
		maxTextureSize = texSize;
	}

	/**
	 * Returns the maximum texture size that will be used by generateData() when
	 * creating a texture atlas for the glyphs.
	 * 
	 * @return the power-of-two max texture size
	 */
	public static int getMaxTextureSize() {
		return maxTextureSize;
	}

	BitmapFont font;

	/**
	 * @author hx 读取安卓系统的ttf配置文件
	 * @return TTFfamily
	 * */
	private void readSysTtfConfig() {
		Properties props = System.getProperties(); // 获得系统属性集
		String osName = props.getProperty("os.name"); // 操作系统名称
		if (osName.toLowerCase().contains("mac")) {
			FileHandle fh = Gdx.files
					.absolute("/System/Library/Fonts/DroidSansFallback.ttf");
			if (fh.exists()) {
				this.ttfs = new TtfFamily();
				this.ttfs.add(fh);
			} else {
				throw new GdxRuntimeException(
						"mac下我没有实现，需要的话自己实现，或者把DroidSansFallback.ttf放到 /System/Library/Fonts下");
			}
		} else if (osName.toLowerCase().contains("windows")) {
			FileHandle fh = Gdx.files
					.absolute("C:\\Windows\\Fonts\\DroidSansFallback.ttf");
			if (fh.exists()) {
				this.ttfs = new TtfFamily();
				this.ttfs.add(fh);
			} else {
				throw new GdxRuntimeException(
						"window下我没有实现，需要的话自己实现，或者把DroidSansFallback.ttf放到 windows/fonts下");
			}
		} else {
			try {
				FileHandle f = Gdx.files
						.absolute("/system/etc/fallback_fonts.xml");

				if (f.exists()) {
					TtfFamily ttfs = new PullTtfPaser().parse(f.read());
					this.ttfs = ttfs;
				} else {
					ttfs = new TtfFamily();
					// System.out.println("不存在");
					// Plugin.i("fallback_fonts.xml 不存在..");

					FileHandle ff = Gdx.files.absolute("/system/fonts/");
					FileHandle[] ffx = ff.list();
					if (ffx.length <= 0) {
						// Plugin.i("直接读取/system/fonts/下的文件..");
						String[] defultFonts = {
								"/system/fonts/DroidSansFallback.ttf",
								"/system/fonts/DroidSans-Bold.ttf",
								"/system/fonts/DroidSans.ttf",
								"/system/fonts/DroidSansMono.ttf",
								"/system/fonts/DroidSerif-Bold.ttf",
								"/system/fonts/DroidSerif-BoldItalic.ttf",
								"/system/fonts/DroidSerif-Italic.ttf",
								"system/fonts/DroidSerif-Regular.ttf"

						};
						for (String path : defultFonts) {
							FileHandle fh = Gdx.files.absolute(path);
							if (fh.exists()) {
								ttfs.add(fh);
							}
						}
					} else {
						// Plugin.i("file list[]读取/system/fonts/下的文件..");
						for (FileHandle fhd : ffx) {
							if (fhd != null) {
								String name = fhd.name();
								String ext = name.substring(name
										.lastIndexOf(".") + 1);
								if (fhd.exists()
										&& ext != null
										&& "ttf".equalsIgnoreCase(ext
												.toLowerCase())) {
									ttfs.add(fhd);
								}
							}
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Creates a new generator from the given TrueType font file. Throws a
	 * {@link GdxRuntimeException} in case loading did not succeed.
	 * 
	 * @param font
	 *            the {@link FileHandle} to the TrueType font file
	 */
	// public FreeTypeFontGeneratorExt(FileHandle font) {
	//
	// filePath = font.pathWithoutExtension();
	// library = FreeType.initFreeType();
	// if (library == null)
	// throw new GdxRuntimeException("Couldn't initialize FreeType");
	// face = FreeType.newFace(library, font, 0);
	// if (face == null)
	// throw new GdxRuntimeException("Couldn't create face for font '"
	// + font + "'");
	// if (checkForBitmapFont()) {
	// return;
	// }
	// if (!FreeType.setPixelSizes(face, 0, 15))
	// throw new GdxRuntimeException("Couldn't set size for font '" + font
	// + "'");
	// }

	public FreeTypeFontGeneratorExt(int size, boolean flip) {
		readSysTtfConfig();
		library = FreeType.initFreeType();
		if (library == null)
			throw new GdxRuntimeException("Couldn't initialize FreeType");

		Iterator<FileHandle> it = ttfs.getTtf().iterator();

		for (; it.hasNext();) {
			FileHandle font = it.next();
			filePath = ttfs.getTtf().get(0).pathWithoutExtension();
			try {
				face = FreeType.newFace(library, font, 0);
			} catch (GdxRuntimeException e) {
				continue;
			}
			if (face == null) {
				continue;
			}
			if (checkForBitmapFont()) {
				continue;
			}
			if (!FreeType.setPixelSizes(face, 0, this.size)) {
				continue;
			}
			break;
		}
		init(size, flip);
	}

	private boolean checkForBitmapFont() {
		if (((face.getFaceFlags() & FreeType.FT_FACE_FLAG_FIXED_SIZES) == FreeType.FT_FACE_FLAG_FIXED_SIZES)
				&& ((face.getFaceFlags() & FreeType.FT_FACE_FLAG_HORIZONTAL) == FreeType.FT_FACE_FLAG_HORIZONTAL)) {
			if (FreeType.loadChar(face, 32, FreeType.FT_LOAD_DEFAULT)) {
				GlyphSlot slot = face.getGlyph();
				if (slot.getFormat() == 1651078259) {
					bitmapped = true;
				}
			}
		}
		return bitmapped;
	}

	/**
	 * Generates a new {@link BitmapFont}, containing glyphs for the given
	 * characters. The size is expressed in pixels. Throws a GdxRuntimeException
	 * in case the font could not be generated. Using big sizes might cause such
	 * an exception. All characters need to fit onto a single texture.
	 * 
	 * @param size
	 *            the size in pixels
	 * @param characters
	 *            the characters the font should contain
	 * @param flip
	 *            whether to flip the font horizontally, see
	 *            {@link BitmapFont#BitmapFont(FileHandle, TextureRegion, boolean)}
	 * @deprecated use {@link #generateFont(FreeTypeFontParameter)} instead
	 */
	public BitmapFont generateFont(int size, String characters, boolean flip) {
		FreeTypeBitmapFontData data = generateData(size, characters, flip, null);
		BitmapFont font = new BitmapFont(data, data.getTextureRegions(), false);
		font.setOwnsTexture(true);
		return font;
	}

	/**
	 * @author hx
	 * 
	 *         初始化 默认加载了所有英文字符和数字
	 * 
	 * 
	 * */

	public void init(int size, boolean flip) {
		this.size = size;
		FreeTypeBitmapFontData data = generateData(size, "", flip, null);
		font = new BitmapFont(data, data.getTextureRegions(), false);
		font.setOwnsTexture(true);
		//appendToFont(DEFAULT_CHARS);
		font = appendToFont(DEFAULT_CHARS);
	}

	/**
	 * Generates a new {@link BitmapFont}. The size is expressed in pixels.
	 * Throws a GdxRuntimeException in case the font could not be generated.
	 * Using big sizes might cause such an exception. All characters need to fit
	 * onto a single texture.
	 * 
	 * @param size
	 *            the size of the font in pixels
	 * @deprecated use {@link #generateFont(FreeTypeFontParameter)} instead
	 */
	public BitmapFont generateFont(int size) {
		return generateFont(size, DEFAULT_CHARS, false);
	}

	/**
	 * Generates a new {@link BitmapFont}. The size is expressed in pixels.
	 * Throws a GdxRuntimeException in case the font could not be generated.
	 * Using big sizes might cause such an exception. All characters need to fit
	 * onto a single texture.
	 * 
	 * @param parameter
	 *            configures how the font is generated
	 */
	public BitmapFont generateFont(FreeTypeFontParameter parameter) {
		FreeTypeBitmapFontData data = generateData(parameter);
		font = new BitmapFont(data, data.getTextureRegions(), false);
		font.setOwnsTexture(true);
		return font;
	}

	/**
	 * @author hx 20140529
	 */
	public synchronized BitmapFont appendToFont(String chars) {
		chars = chars.replaceAll("(?s)(.)(?=.*\\1)", "");// 去掉重复字符
		return appendToFont(chars, null);
	}

	public synchronized BitmapFont appendToFont(String chars, BitmapFont ofont) {
		if (par == null || data == null) {
			throw new GdxRuntimeException(
					"You should initialize FreeType before append!");
		}
		boolean ownsAtlas = true;
		par.characters = chars;
		// 提前判断字符是否已经创建
		String packPrefix = ownsAtlas ? ""
				: (filePath + '_' + par.size + (par.flip ? "_flip_" : '_'));

		for (int i = 0; i < par.characters.length(); i++) {
			char c = par.characters.charAt(i);
			String name = packPrefix + c;
			if (par.packer.getRect(name) == null) {
				// 如果有字符没有被创建，则中断当前判断
				break;
			} else if (i == par.characters.length() - 1) {
				// 如果所有字符都被创建了，中断整个方法
				return font;
			}
		}
		//
		data = appendData();
		font = new BitmapFont(data, data.getTextureRegions(), false);
		if (ofont != null) {
			font.setColor(ofont.getColor());
			font.setScale(ofont.getScaleX(), ofont.getScaleY());
		}
		font.setOwnsTexture(true);
		// long de = new Date().getTime();
		return font;
	}

	/**
	 * Uses ascender and descender of font to calculate real height that makes
	 * all glyphs to fit in given pixel size. Source:
	 * http://nothings.org/stb/stb_truetype.h / stbtt_ScaleForPixelHeight
	 */
	public int scaleForPixelHeight(int size) {
		if (!bitmapped && !FreeType.setPixelSizes(face, 0, size))
			throw new GdxRuntimeException("Couldn't set size for font");
		SizeMetrics fontMetrics = face.getSize().getMetrics();
		int ascent = FreeType.toInt(fontMetrics.getAscender());
		int descent = FreeType.toInt(fontMetrics.getDescender());
		return size * size / (ascent - descent);
	}

	public class GlyphAndBitmap {
		public Glyph glyph;
		public Bitmap bitmap;
	}

	/**
	 * Returns null if glyph was not found. If there is nothing to render, for
	 * example with various space characters, then bitmap is null.
	 */
	public GlyphAndBitmap generateGlyphAndBitmap(int c, int size, boolean flip) {
		if (!bitmapped && !FreeType.setPixelSizes(face, 0, size))
			throw new GdxRuntimeException("Couldn't set size for font");

		SizeMetrics fontMetrics = face.getSize().getMetrics();
		int baseline = FreeType.toInt(fontMetrics.getAscender());

		// Check if character exists in this font.
		// 0 means 'undefined character code'
		if (FreeType.getCharIndex(face, c) == 0) {
			return null;
		}

		// Try to load character
		if (!FreeType.loadChar(face, c, FreeType.FT_LOAD_DEFAULT)) {
			throw new GdxRuntimeException("Unable to load character!");
		}

		GlyphSlot slot = face.getGlyph();

		// Try to render to bitmap
		Bitmap bitmap;
		if (bitmapped) {
			bitmap = slot.getBitmap();
		} else if (!FreeType.renderGlyph(slot, FreeType.FT_RENDER_MODE_LIGHT)) {
			bitmap = null;
		} else {
			bitmap = slot.getBitmap();
		}

		GlyphMetrics metrics = slot.getMetrics();

		Glyph glyph = new Glyph();
		if (bitmap != null) {
			glyph.width = bitmap.getWidth();
			glyph.height = bitmap.getRows();
		} else {
			glyph.width = 0;
			glyph.height = 0;
		}
		glyph.xoffset = slot.getBitmapLeft();
		glyph.yoffset = flip ? -slot.getBitmapTop() + baseline
				: -(glyph.height - slot.getBitmapTop()) - baseline;
		glyph.xadvance = FreeType.toInt(metrics.getHoriAdvance());
		glyph.srcX = 0;
		glyph.srcY = 0;
		glyph.id = c;

		GlyphAndBitmap result = new GlyphAndBitmap();
		result.glyph = glyph;
		result.bitmap = bitmap;
		return result;
	}

	/**
	 * Generates a new {@link BitmapFontData} instance, expert usage only.
	 * Throws a GdxRuntimeException in case something went wrong.
	 * 
	 * @param size
	 *            the size in pixels
	 */
	public FreeTypeBitmapFontData generateData(int size) {
		return generateData(size, DEFAULT_CHARS, false, null);
	}

	/**
	 * Generates a new {@link BitmapFontData} instance, expert usage only.
	 * Throws a GdxRuntimeException in case something went wrong.
	 * 
	 * @param size
	 *            the size in pixels
	 * @param characters
	 *            the characters the font should contain
	 * @param flip
	 *            whether to flip the font horizontally, see
	 *            {@link BitmapFont#BitmapFont(FileHandle, TextureRegion, boolean)}
	 * @deprecated use {@link #generateData(FreeTypeFontParameter)} instead
	 */
	public FreeTypeBitmapFontData generateData(int size, String characters,
			boolean flip) {
		return generateData(size, characters, flip, null);
	}

	/**
	 * Generates a new {@link BitmapFontData} instance, expert usage only.
	 * Throws a GdxRuntimeException in case something went wrong.
	 * 
	 * @param size
	 *            the size in pixels
	 * @param characters
	 *            the characters the font should contain
	 * @param flip
	 *            whether to flip the font horizontally, see
	 *            {@link BitmapFont#BitmapFont(FileHandle, TextureRegion, boolean)}
	 * @param packer
	 *            the optional PixmapPacker to use
	 * @deprecated use {@link #generateData(FreeTypeFontParameter)} instead
	 */
	public FreeTypeBitmapFontData generateData(int size, String characters,
			boolean flip, PixmapPacker packer) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.characters = characters;
		parameter.flip = flip;
		parameter.packer = packer;
		this.par = parameter;
		return generateData(parameter);
	}

	/**
	 * Generates a new {@link BitmapFontData} instance, expert usage only.
	 * Throws a GdxRuntimeException in case something went wrong.
	 * 
	 * @param parameter
	 *            configures how the font is generated
	 */
	public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter) {
		parameter = parameter == null ? new FreeTypeFontParameter() : parameter;

		FreeTypeBitmapFontData data = new FreeTypeBitmapFontData();
		if (!bitmapped && !FreeType.setPixelSizes(face, 0, parameter.size))
			throw new GdxRuntimeException("Couldn't set size for font");

		// set general font data
		SizeMetrics fontMetrics = face.getSize().getMetrics();
		data.flipped = parameter.flip;
		data.ascent = FreeType.toInt(fontMetrics.getAscender());
		data.descent = FreeType.toInt(fontMetrics.getDescender());
		data.lineHeight = FreeType.toInt(fontMetrics.getHeight());
		float baseLine = data.ascent;

		// if bitmapped
		if (bitmapped && (data.lineHeight == 0)) {
			for (int c = 32; c < (32 + face.getNumGlyphs()); c++) {
				if (FreeType.loadChar(face, c, FreeType.FT_LOAD_DEFAULT)) {
					int lh = FreeType.toInt(face.getGlyph().getMetrics()
							.getHeight());
					data.lineHeight = (lh > data.lineHeight) ? lh
							: data.lineHeight;
				}
			}
		}

		// determine space width and set glyph
		if (FreeType.loadChar(face, ' ', FreeType.FT_LOAD_DEFAULT)) {
			data.spaceWidth = FreeType.toInt(face.getGlyph().getMetrics()
					.getHoriAdvance());
		} else {
			data.spaceWidth = face.getMaxAdvanceWidth(); // FIXME possibly very
															// wrong :)
		}
		Glyph spaceGlyph = new Glyph();
		spaceGlyph.xadvance = (int) data.spaceWidth;
		spaceGlyph.id = (int) ' ';
		data.setGlyph(' ', spaceGlyph);

		// determine x-height
		for (char xChar : BitmapFont.xChars) {
			if (!FreeType.loadChar(face, xChar, FreeType.FT_LOAD_DEFAULT))
				continue;
			data.xHeight = FreeType.toInt(face.getGlyph().getMetrics()
					.getHeight());
			break;
		}
		// if (data.xHeight == 0)
		// throw new GdxRuntimeException("No x-height character found in font");
		for (char capChar : BitmapFont.capChars) {
			if (!FreeType.loadChar(face, capChar, FreeType.FT_LOAD_DEFAULT))
				continue;
			data.capHeight = FreeType.toInt(face.getGlyph().getMetrics()
					.getHeight());
			break;
		}

		// determine cap height
		if (!bitmapped && data.capHeight == 1)
			throw new GdxRuntimeException("No cap character found in font");
		data.ascent = data.ascent - data.capHeight;
		data.down = -data.lineHeight;
		if (parameter.flip) {
			data.ascent = -data.ascent;
			data.down = -data.down;
		}

		boolean ownsAtlas = false;

		PixmapPacker packer = parameter.packer;

		if (packer == null) {
			// generate the glyphs
			// int maxGlyphHeight = (int) Math.ceil(data.lineHeight);
			int pageWidth = 512;// MathUtils
			// .nextPowerOfTwo((int)Math.sqrt(maxGlyphHeight * maxGlyphHeight *
			// parameter.characters.length()));

			if (maxTextureSize > 0)
				pageWidth = Math.min(pageWidth, maxTextureSize);

			ownsAtlas = true;
			packer = new PixmapPacker(pageWidth, pageWidth, Format.RGBA8888, 2,
					false);

		}

		// to minimize collisions we'll use this format :
		// pathWithoutExtension_size[_flip]_glyph
		String packPrefix = ownsAtlas ? ""
				: (filePath + '_' + parameter.size + (parameter.flip ? "_flip_"
						: '_'));

		for (int i = 0; i < parameter.characters.length(); i++) {
			char c = parameter.characters.charAt(i);
			if (!FreeType.loadChar(face, c, FreeType.FT_LOAD_DEFAULT)) {
				Gdx.app.log("FreeTypeFontGenerator", "Couldn't load char '" + c
						+ "'");
				continue;
			}
			if (!FreeType.renderGlyph(face.getGlyph(),
					FreeType.FT_RENDER_MODE_NORMAL)) {
				Gdx.app.log("FreeTypeFontGenerator", "Couldn't render char '"
						+ c + "'");
				continue;
			}
			GlyphSlot slot = face.getGlyph();
			GlyphMetrics metrics = slot.getMetrics();
			Bitmap bitmap = slot.getBitmap();
			Pixmap pixmap = bitmap.getPixmap(Format.RGBA8888);
			Glyph glyph = new Glyph();
			glyph.id = (int) c;
			glyph.width = pixmap.getWidth();
			glyph.height = pixmap.getHeight();
			glyph.xoffset = slot.getBitmapLeft();
			glyph.yoffset = parameter.flip ? -slot.getBitmapTop()
					+ (int) baseLine : -(glyph.height - slot.getBitmapTop())
					- (int) baseLine;
			glyph.xadvance = FreeType.toInt(metrics.getHoriAdvance());

			if (bitmapped) {
				pixmap.setColor(Color.CLEAR);
				pixmap.fill();
				ByteBuffer buf = bitmap.getBuffer();
				for (int h = 0; h < glyph.height; h++) {
					int idx = h * bitmap.getPitch();
					for (int w = 0; w < (glyph.width + glyph.xoffset); w++) {
						int bit = (buf.get(idx + (w / 8)) >>> (7 - (w % 8))) & 1;
						pixmap.drawPixel(w, h,
								((bit == 1) ? Color.WHITE.toIntBits()
										: Color.CLEAR.toIntBits()));
					}
				}

			}

			String name = packPrefix + c;
			Rectangle rect = packer.pack(name, pixmap);

			// determine which page it was packed into
			int pIndex = packer.getPageIndex(name);
			if (pIndex == -1) // we should not get here
				throw new IllegalStateException(
						"packer was not able to insert '" + name
								+ "' into a page");

			glyph.page = pIndex;
			glyph.srcX = (int) rect.x;
			glyph.srcY = (int) rect.y;

			data.setGlyph(c, glyph);
			pixmap.dispose();
		}

		// generate kerning
		// for (int i = 0; i < parameter.characters.length(); i++) {
		// for (int j = 0; j < parameter.characters.length(); j++) {
		// char firstChar = parameter.characters.charAt(i);
		// Glyph first = data.getGlyph(firstChar);
		// if (first == null) continue;
		// char secondChar = parameter.characters.charAt(j);
		// Glyph second = data.getGlyph(secondChar);
		// if (second == null) continue;
		// int kerning = FreeType.getKerning(face, FreeType.getCharIndex(face,
		// firstChar),
		// FreeType.getCharIndex(face, secondChar), 0);
		// if (kerning == 0) continue;
		// first.setKerning(secondChar, FreeType.toInt(kerning));
		// }
		// }

		if (ownsAtlas) {
			Array<Page> pages = packer.getPages();
			data.regions = new TextureRegion[20];

			for (int i = 0; i < pages.size; i++) {
				Page p = pages.get(i);

				Texture tex = new Texture(new PixmapTextureData(p.getPixmap(),
						p.getPixmap().getFormat(), parameter.genMipMaps, false,
						true)) {
					@Override
					public void dispose() {
						super.dispose();
						getTextureData().consumePixmap().dispose();
					}
				};
				tex.setFilter(parameter.minFilter, parameter.magFilter);

				data.regions[i] = new TextureRegion(tex);
			}
		}
		this.data = data;
		parameter.packer = packer;
		this.par = parameter;

		return data;
	}

	/**
	 * @author hx 20140529 append some chars into font, if didn't initialize @throws
	 *         You should initialize FreeType before append!
	 * 
	 *         20140530
	 *         修改为循环读取系统默认ttf文件，一般使用场景下，不会经常切换语系，所以face不会频繁切换，如遇频繁切换语系(英文不算
	 *         )可能会导致性能降低
	 * @param parameter
	 *            configures how the font is generated
	 */
	// FreeTypeBitmapFontData

	private FreeTypeBitmapFontData appendData() {

		FreeTypeFontParameter parameter = this.par;
		if (parameter == null || data == null) {
			throw new GdxRuntimeException(
					"You should initialize FreeType before append!");
		}

		// set general font data
		SizeMetrics fontMetrics = face.getSize().getMetrics();
		data.flipped = parameter.flip;
		data.ascent = FreeType.toInt(fontMetrics.getAscender());
		data.descent = FreeType.toInt(fontMetrics.getDescender());
		data.lineHeight = FreeType.toInt(fontMetrics.getHeight());
		float baseLine = data.ascent;

		// determine cap height
		if (!bitmapped && data.capHeight == 1)
			throw new GdxRuntimeException("No cap character found in font");
		data.ascent = data.ascent - data.capHeight;
		data.down = -data.lineHeight;
		if (parameter.flip) {
			data.ascent = -data.ascent;
			data.down = -data.down;
		}

		boolean ownsAtlas = true;

		PixmapPacker packer = parameter.packer;

		String packPrefix = ownsAtlas ? ""
				: (filePath + '_' + parameter.size + (parameter.flip ? "_flip_"
						: '_'));

		for (int i = 0; i < parameter.characters.length(); i++) {
			char c = parameter.characters.charAt(i);
			String name = packPrefix + c;
			// 如果这个字已经在font里了，则跳过这个字
			if (packer.getRect(name) != null) {
				continue;
			}
			int index = 0;
			for (int x = 0; x < ttfs.getTtf().size(); x++) {
				// System.out.println("fp===" + x + filePath);
				// 先用默认font进行匹配
				boolean flag = true;
				index = FreeType.getCharIndex(face, c);
				if (index == 0) {
					flag = false;
				}

				if (!FreeType.loadChar(face, c, FreeType.FT_LOAD_DEFAULT)) {
					flag = false;
				}
				if (!FreeType.renderGlyph(face.getGlyph(),
						FreeType.FT_RENDER_MODE_NORMAL)) {
					flag = false;
				}
				if (!flag) {// 如果默认字体匹配失败，则尝试用其他字体
					FileHandle font = ttfs.getTtf().get(x);
					filePath = font.pathWithoutExtension();
					try {
						// 解决部分手机的ttf一旦读取就会报错的问题
						face = FreeType.newFace(library, font, 0);
					} catch (GdxRuntimeException e) {
						continue;
					}
					FreeType.setPixelSizes(face, 0, this.size);
				} else {
					break;
				}
			}

			if (index == 0) {
				Gdx.app.log("freetype", "char '" + name + "' not loaded!");
				break;
			}

			GlyphSlot slot = face.getGlyph();
			GlyphMetrics metrics = slot.getMetrics();
			Bitmap bitmap = slot.getBitmap();
			Pixmap pixmap = bitmap.getPixmap(Format.RGBA8888);
			Glyph glyph = new Glyph();
			glyph.id = (int) c;
			glyph.width = pixmap.getWidth();
			glyph.height = pixmap.getHeight();
			glyph.xoffset = slot.getBitmapLeft();
			glyph.yoffset = parameter.flip ? -slot.getBitmapTop()
					+ (int) baseLine : -(glyph.height - slot.getBitmapTop())
					- (int) baseLine;
			glyph.xadvance = FreeType.toInt(metrics.getHoriAdvance());

			if (bitmapped) {
				pixmap.setColor(Color.CLEAR);
				pixmap.fill();
				ByteBuffer buf = bitmap.getBuffer();
				for (int h = 0; h < glyph.height; h++) {
					int idx = h * bitmap.getPitch();
					for (int w = 0; w < (glyph.width + glyph.xoffset); w++) {
						int bit = (buf.get(idx + (w / 8)) >>> (7 - (w % 8))) & 1;
						pixmap.drawPixel(w, h,
								((bit == 1) ? Color.WHITE.toIntBits()
										: Color.CLEAR.toIntBits()));
					}
				}

			}

			Rectangle rect = packer.pack(name, pixmap);

			// determine which page it was packed into
			int pIndex = packer.getPageIndex(name);
			if (pIndex == -1) // we should not get here
				throw new IllegalStateException(
						"packer was not able to insert '" + name
								+ "' into a page");

			glyph.page = pIndex;
			glyph.srcX = (int) rect.x;
			glyph.srcY = (int) rect.y;

			data.setGlyph(c, glyph);
			pixmap.dispose();
		}

		if (ownsAtlas) {
			Array<Page> pages = packer.getPages();
			// data.regions = new TextureRegion[pages.size];
			for (int i = 0; i < pages.size; i++) {
				Page p = pages.get(i);
				// this.pxmap =p.getPixmap();
				Texture tex = new Texture(new PixmapTextureData(p.getPixmap(),
						p.getPixmap().getFormat(), parameter.genMipMaps, false,
						true)) {
					@Override
					public void dispose() {
						super.dispose();
						getTextureData().consumePixmap().dispose();
					}
				};
				
				tex.setFilter(parameter.minFilter, parameter.magFilter);
				t = tex;
				data.regions[i] = new TextureRegion(tex);
			}
		}
		return data;
	}

	/**
	 * Cleans up all resources of the generator. Call this if you no longer use
	 * the generator.
	 */
	@Override
	public void dispose() {
		FreeType.doneFace(face);
		FreeType.doneFreeType(library);
	}

	/**
	 * 创建文本
	 * @param text
	 * @return
	 */
	public Label createLabel(String text) {
	    appendToFont(text);
		return new Label(text, new LabelStyle(font, Color.WHITE));
	}
	
	/**
	 * 
	 * <pre>
	 * 取出字体
	 * 
	 * date: 2014-12-26
	 * </pre>
	 * @author caohao
	 * @return
	 */
	public BitmapFont getBitmapFont(){
	    return font;
	}

	/**
	 * {@link BitmapFontData} used for fonts generated via the
	 * {@link FreeTypeFontGenerator}. The texture storing the glyphs is held in
	 * memory, thus the {@link #getImagePaths()} and {@link #getFontFile()}
	 * methods will return null.
	 * 
	 * @author mzechner
	 */
	public static class FreeTypeBitmapFontData extends BitmapFontData {
		TextureRegion[] regions;

		/**
		 * Returns the first texture region. Use getTextureRegions() instead
		 * 
		 * @return the first texture region in the array
		 * @deprecated use getTextureRegions() instead
		 */
		@Deprecated
		public TextureRegion getTextureRegion() {
			return regions[0];
		}

		public TextureRegion[] getTextureRegions() {
			return regions;
		}
	}

	/**
	 * Parameter container class that helps configure how
	 * {@link FreeTypeBitmapFontData} and {@link BitmapFont} instances are
	 * generated.
	 * 
	 * The packer field is for advanced usage, where it is necessary to pack
	 * multiple BitmapFonts (i.e. styles, sizes, families) into a single Texture
	 * atlas. If no packer is specified, the generator will use its own
	 * PixmapPacker to pack the glyphs into a power-of-two sized texture, and
	 * the resulting {@link FreeTypeBitmapFontData} will have a valid
	 * {@link TextureRegion} which can be used to construct a new
	 * {@link BitmapFont}.
	 * 
	 * @author siondream
	 */
	public static class FreeTypeFontParameter {
		/** The size in pixels */
		public int size = 30;
		/** The characters the font should contain */
		public String characters = DEFAULT_CHARS;
		/** The optional PixmapPacker to use */
		public PixmapPacker packer = null;
		/** Whether to flip the font horizontally */
		public boolean flip = false;
		/** Whether or not to generate mip maps for the resulting texture */
		public boolean genMipMaps = false;
		/** Minification filter */
		public TextureFilter minFilter = TextureFilter.Linear;
		/** Magnification filter */
		public TextureFilter magFilter = TextureFilter.Linear;
	}

	public class PullTtfPaser {
		String abspath = "/system/fonts/";

		public TtfFamily parse(InputStream is) throws Exception {
			TtfFamily ttfs = new TtfFamily();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element rootElement = doc.getDocumentElement();
			NodeList items = rootElement.getElementsByTagName("file");
			for (int i = 0; i < items.getLength(); i++) {

				Node item = items.item(i);
				String filename = item.getFirstChild().getTextContent();
				if (filename == null || "".equals(filename.trim())) {
					continue;
				} else {

					FileHandle fh = Gdx.files.absolute(abspath + filename);
					if (fh.exists()) {
						ttfs.add(fh);
					}

				}
			}
			return ttfs;
		}
	}

	public class TtfFamily {
		private List<FileHandle> ttfs = new ArrayList<FileHandle>();

		public List<FileHandle> getTtf() {
			return ttfs;
		}

		public void setTtfs(List<FileHandle> ttfs) {
			this.ttfs = ttfs;
		}

		public void add(FileHandle f) {
			this.ttfs.add(f);
		}
	}
}
