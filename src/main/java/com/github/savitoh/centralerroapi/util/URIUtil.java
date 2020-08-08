package com.github.savitoh.centralerroapi.util;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public final class URIUtil {

    private static final String TEMPLATE_TOKENIZE_PATH = "/{#}";

    private URIUtil() {
    }

    public static String criarPathTemplate(@NonNull String nomePath) {
        Assert.hasText(nomePath, "#nomePath não pode ser vazio");
        return TEMPLATE_TOKENIZE_PATH.replace("#", nomePath);
    }
}
