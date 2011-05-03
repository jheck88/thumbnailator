package net.coobird.thumbnailator.builders;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.geometry.Region;
import net.coobird.thumbnailator.resizers.Resizer;
import net.coobird.thumbnailator.resizers.Resizers;

/**
 * A builder for generating {@link ThumbnailParameter}.
 * <p>
 * The default values assigned to the {@link ThumbnailParameter} created by
 * the {@link ThumbnailParameterBuilder} are as follows:
 * <p>
 * <dl>
 * <dt>width</dt>
 * <dd>Unassigned. Must be set by the {@link #size(int, int)} method.</dd>
 * <dt>height</dt>
 * <dd>Unassigned. Must be set by the {@link #size(int, int)} method.</dd>
 * <dt>scaling factor</dt>
 * <dd>Unassigned. Must be set by the {@link #scale(double)} method.</dd>
 * <dt>source region</dt>
 * <dd>Uses the entire source image.</dd>
 * <dt>image type</dt>
 * <dd>See {@link ThumbnailParameter#DEFAULT_IMAGE_TYPE}. Same as
 * {@link BufferedImage#TYPE_INT_ARGB}.</dd>
 * <dt>aspect ratio</dt>
 * <dd>Maintain the aspect ratio of the original image.</dd>
 * <dt>output quality</dt>
 * <dd>See {@link ThumbnailParameter#DEFAULT_QUALITY}.</dd>
 * <dt>output format</dt>
 * <dd>See {@link ThumbnailParameter#ORIGINAL_FORMAT}. Maintains the same
 * image format as the original image.</dd>
 * <dt>output format type</dt>
 * <dd>See {@link ThumbnailParameter#DEFAULT_FORMAT_TYPE}. Uses the default
 * format type of the codec used to create the thumbnail image.</dd>
 * <dt>image filters</dt>
 * <dd>None.</dd>
 * <dt>resizer</dt>
 * <dd>{@link Resizers#PROGRESSIVE} is used.</dd>
 * </dl>
 * 
 * @author coobird
 *
 */
public final class ThumbnailParameterBuilder
{
	private static final int UNINITIALIZED = -1;
	
	private int width = UNINITIALIZED;
	private int height = UNINITIALIZED;
	private double scalingFactor = Double.NaN;
	private int imageType = ThumbnailParameter.DEFAULT_IMAGE_TYPE;
	private boolean keepAspectRatio = true;
	private float thumbnailQuality = ThumbnailParameter.DEFAULT_QUALITY;
	private String thumbnailFormat = ThumbnailParameter.ORIGINAL_FORMAT;
	private String thumbnailFormatType = ThumbnailParameter.DEFAULT_FORMAT_TYPE;
	private List<ImageFilter> filters = Collections.emptyList();
	private Resizer resizer = Resizers.PROGRESSIVE;
	private Region sourceRegion = null;
	
	/**
	 * Creates an instance of a {@link ThumbnailParameterBuilder}.
	 */
	public ThumbnailParameterBuilder()
	{
	}
	
	/**
	 * Sets the image type fo the thumbnail.
	 * 
	 * @param type			The image type of the thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder imageType(int type)
	{
		imageType = type;
		return this;
	}
	
	/**
	 * Sets the size of the thumbnail.
	 * 
	 * @param size		The dimensions of the thumbnail.
	 * @return			A reference to this object.
	 */
	public ThumbnailParameterBuilder size(Dimension size)
	{
		size(size.width, size.height);
		return this;
	}
	
	/**
	 * Sets the size of the thumbnail.
	 * 
	 * @param width		The width of the thumbnail.
	 * @param height	The height of the thumbnail.
	 * @return			A reference to this object.
	 * @throws IllegalArgumentException	If the widht or height is less than 0.
	 */
	public ThumbnailParameterBuilder size(int width, int height)
	{
		if (width < 0)
		{
			throw new IllegalArgumentException("Width must be greater than 0.");
		}
		if (height < 0)
		{
			throw new IllegalArgumentException("Height must be greater than 0.");
		}
		
		this.width = width;
		this.height = height;
		return this;
	}
	
	/**
	 * Sets the scaling factor of the thumbnail.
	 * 
	 * @param scalingFactor		The scaling factor of the thumbnail.
	 * @return					A reference to this object.
	 * @throws IllegalArgumentException		If the scaling factor is not a 
	 * 										rational number, or if it is less
	 * 										than {@code 0.0}.
	 */
	public ThumbnailParameterBuilder scale(double scalingFactor)
	{
		if (scalingFactor <= 0.0)
		{
			throw new IllegalArgumentException("Scaling factor is less than or equal to 0.");
		} 
		else if (Double.isNaN(scalingFactor) || Double.isInfinite(scalingFactor))
		{
			throw new IllegalArgumentException("Scaling factor must be a rational number.");
		} 
		
		this.scalingFactor = scalingFactor;
		return this;
	}
	
	/**
	 * Sets the region of the source image to use when creating a thumbnail.
	 * 
	 * @param sourceRegion		The region of the source image to use when
	 * 							creating a thumbnail.
	 * @return			A reference to this object.
	 */
	public ThumbnailParameterBuilder region(Region sourceRegion)
	{
		this.sourceRegion = sourceRegion;
		return this;
	}
	
	/**
	 * Sets whether or not the thumbnail is to maintain the aspect ratio of
	 * the original image.
	 * 
	 * @param keep		{@code true} if the aspect ratio of the original image
	 * 					is to be maintained in the thumbnail, {@code false}
	 * 					otherwise.
	 * @return			A reference to this object.
	 */
	public ThumbnailParameterBuilder keepAspectRatio(boolean keep)
	{
		this.keepAspectRatio = keep;
		return this;
	}
	
	/**
	 * Sets the compression quality setting of the thumbnail.
	 * <p>
	 * An acceptable value is in the range of {@code 0.0f} to {@code 1.0f},
	 * where {@code 0.0f} is for the lowest quality setting and {@code 1.0f} for
	 * the highest quality setting.
	 * <p>
	 * If the default compression quality is to be used, then the value 
	 * {@link ThumbnailParameter#DEFAULT_QUALITY} should be used.
	 * 
	 * @param quality		The compression quality setting of the thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder quality(float quality)
	{
		this.thumbnailQuality = quality;
		return this;
	}

	/**
	 * Sets the output format of the thumbnail.
	 * 
	 * @param format		The output format of the thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder format(String format)
	{
		this.thumbnailFormat = format;
		return this;
	}
	
	/**
	 * Sets the output format type of the thumbnail.
	 * 
	 * @param formatType	The output format type of the thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder formatType(String formatType)
	{
		this.thumbnailFormatType = formatType;
		return this;
	}
	
	/**
	 * Sets the {@link ImageFilter}s to apply to the thumbnail.
	 * <p>
	 * These filters will be applied after the original image is resized.
	 * 
	 * @param filters		The output format type of the thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder filters(List<ImageFilter> filters)
	{
		if (filters == null)
		{
			throw new NullPointerException("Filters is null.");
		}
		
		this.filters = filters;
		return this;
	}
	
	/**
	 * Sets the {@link Resizer} to use when performing the resizing operation
	 * to create the thumbnail.
	 * 
	 * @param resizer		The {@link Resizer} to use when creating the
	 * 						thumbnail.
	 * @return				A reference to this object.
	 */
	public ThumbnailParameterBuilder resizer(Resizer resizer)
	{
		if (resizer == null)
		{
			throw new NullPointerException("Resizer is null.");
		}
		
		this.resizer = resizer;
		return this;
	}

	/**
	 * Returns a {@link ThumbnailParameter} from the parameters which are
	 * currently set.
	 * <p>
	 * This method will throw a {@link IllegalArgumentException} required 
	 * parameters for the {@link ThumbnailParameter} have not been set.
	 * 
	 * @return		A {@link ThumbnailParameter} with parameters set through
	 * 				the use of this builder.
	 * @throws IllegalStateException	If neither the size nor the scaling
	 * 									factor has been set.
	 */
	public ThumbnailParameter build()
	{
		if (!Double.isNaN(scalingFactor))
		{
			// If scaling factor has been set.
			return new ThumbnailParameter(
					scalingFactor,
					sourceRegion,
					keepAspectRatio,
					thumbnailFormat,
					thumbnailFormatType,
					thumbnailQuality,
					imageType,
					filters,
					resizer
			);
			
		}
		else if (width != UNINITIALIZED && height != UNINITIALIZED)
		{
			return new ThumbnailParameter(
					new Dimension(width, height),
					sourceRegion,
					keepAspectRatio,
					thumbnailFormat,
					thumbnailFormatType,
					thumbnailQuality,
					imageType,
					filters,
					resizer
			);
		}
		else
		{
			throw new IllegalStateException(
					"The size nor the scaling factor has been set."
			);
		}
	}
}