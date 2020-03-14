package io.mattmoore.kimage

import java.io.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.imageio.ImageIO

enum class ImageType {
    JPEG,
    PNG;

    fun toAWT(): Int = when (this) {
        ImageType.JPEG -> TYPE_INT_RGB
        ImageType.PNG -> TYPE_INT_ARGB
    }
}

class KImage {
    companion object {
        fun load(inputStream: InputStream, type: ImageType): BufferedImage {
            inputStream.use {
                val ins = ImageIO.createImageInputStream(inputStream)
                val reader = ImageIO.getImageReaders(ins).next()
                try {
                    reader.input = ins
                    val param = reader.defaultReadParam.apply {
                        destination = BufferedImage(reader.getWidth(0), reader.getHeight(0), type.toAWT())
                    }
                    return reader.read(0, param)
                } finally {
                    reader.dispose()
                }
            }
        }
    }
}
