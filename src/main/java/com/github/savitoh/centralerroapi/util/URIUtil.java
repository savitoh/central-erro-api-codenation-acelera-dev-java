package com.github.savitoh.centralerroapi.util;

import org.springframework.util.Assert;

public final class URIUtil {

    private static final String TEMPLATE_TOKENIZE_PATH = "/{#}";

    private URIUtil() {
    }

    public static String criarPathTemplate(String pathName) {
        Assert.hasText(pathName, "#pathName não pode ser vazio");
        return TEMPLATE_TOKENIZE_PATH.replace("#", pathName);
    }
}
