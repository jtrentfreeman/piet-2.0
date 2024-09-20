package com.frejt.piet.utils.reader;

import com.frejt.piet.exception.ContentTypeNotFoundException;

/**
 * Content types that are supported by the interpreter.
 */
public enum ContentType {

    PPM("image/x-portable-pixmap"),
    PNG("image/png");

    String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    /**
     * Gets the ContentType from it's String representation.
     * 
     * @param contentType
     * @return
     * @throws ContentTypeNotFoundException
     */
    public static ContentType getContentType(String contentType) throws ContentTypeNotFoundException {
       
        for(ContentType type : ContentType.values()) {
            if(contentType.equals(type.getContentType())) {
                return type;
            }
        }
        
        throw new ContentTypeNotFoundException("Content type " + contentType + " is not currently supported");
    }
    
}
