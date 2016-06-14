package com.dou361.update.model;

/**
 * @author Administrator
 */
public interface UpdateParser {

    <T extends Update> T parse(String httpResponse);
}
