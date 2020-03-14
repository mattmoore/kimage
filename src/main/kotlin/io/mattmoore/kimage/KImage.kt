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
        JPEG -> TYPE_INT_RGB
        PNG -> TYPE_INT_ARGB
    }

    fun toFormat(): String = when (this) {
        JPEG -> "jpeg"
        PNG -> "png"
    }
}

class KImage {
    companion object {
        fun load(inputStream: InputStream, type: ImageType): ByteArray {
            inputStream.use {
                val ins = ImageIO.createImageInputStream(inputStream)
                val reader = ImageIO.getImageReaders(ins).next()
                try {
                    reader.input = ins
                    val bufferedImage = BufferedImage(reader.getWidth(0), reader.getHeight(0), type.toAWT())
                    var byteArrayOutputStream = ByteArrayOutputStream()
                    ImageIO.write(bufferedImage, type.toFormat(), byteArrayOutputStream)
                    return byteArrayOutputStream.toByteArray()
                } finally {
                    reader.dispose()
                }
            }
        }
    }
}
