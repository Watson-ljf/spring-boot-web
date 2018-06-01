package com.watson.core.filter.wrapper;

import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class ModifiableRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String[]> modifiableParameters;
    private Map<String, String[]> allParameters = null;
    private byte[] buffer;


    /**
     * Create a new request wrapper that will merge additional parameters into
     * the request object without prematurely reading parameters from the
     * original request.
     *
     * @param request
     */
    public ModifiableRequestWrapper(final HttpServletRequest request) {
        super(request);
        modifiableParameters = new TreeMap<>();

        // build form data
        allParameters = request.getParameterMap();

        // build form body
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = request.getInputStream();
            byte[] buff = new byte[2048];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                byteArrayOutputStream.write(buff, 0, length);
            }
            buffer = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addParameter(String key, String... value) {
        this.modifiableParameters.put(key, value);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new DelegatingServletInputStream(new ByteArrayInputStream(buffer));
    }

    @Override
    public String getParameter(final String name) {
        String[] strings = getParameterMap().get(name);
        if (strings != null && strings.length > 0) {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (allParameters == null) {
            allParameters = new TreeMap<>();
            allParameters.putAll(super.getParameterMap());
            allParameters.putAll(modifiableParameters);
        }
        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        return getParameterMap().get(name);
    }
}