/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.core.progmodel.facets.value.bigdecimal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.profiles.Localization;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.commons.exceptions.IsisException;
import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.object.parseable.TextEntryParseException;
import org.apache.isis.core.progmodel.facets.object.value.ValueSemanticsProviderAndFacetAbstract;
import org.apache.isis.core.progmodel.facets.object.value.ValueSemanticsProviderContext;

public class BigDecimalValueSemanticsProvider extends ValueSemanticsProviderAndFacetAbstract<BigDecimal> implements BigDecimalValueFacet {

    private static Class<? extends Facet> type() {
        return BigDecimalValueFacet.class;
    }

    private static final int TYPICAL_LENGTH = 19;
    private static final boolean IMMUTABLE = true;
    private static final boolean EQUAL_BY_CONTENT = true;
    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(0);

    private final NumberFormat format;

    /**
     * Required because implementation of {@link Parser} and
     * {@link EncoderDecoder}.
     */
    public BigDecimalValueSemanticsProvider() {
        this(null, null, null);
    }

    public BigDecimalValueSemanticsProvider(final FacetHolder holder, final IsisConfiguration configuration, final ValueSemanticsProviderContext context) {
        super(type(), holder, BigDecimal.class, TYPICAL_LENGTH, IMMUTABLE, EQUAL_BY_CONTENT, DEFAULT_VALUE, configuration, context);
        format = determineNumberFormat("value.format.decimal");
    }

    public void setLocale(final Locale l) {
        // TODO Auto-generated method stub

    }

    // //////////////////////////////////////////////////////////////////
    // Parser
    // //////////////////////////////////////////////////////////////////

    @Override
    protected BigDecimal doParse(final Object context, final String entry) {
        try {
            return new BigDecimal(entry);
        } catch (final NumberFormatException e) {
            throw new TextEntryParseException("Not an decimal " + entry, e);
        }
    }

    @Override
    public String titleString(final Object object, final Localization localization) {
        return titleString(format, object);
    }

    @Override
    public String titleStringWithMask(final Object value, final String usingMask) {
        return titleString(new DecimalFormat(usingMask), value);
    }

    // //////////////////////////////////////////////////////////////////
    // EncoderDecoder
    // //////////////////////////////////////////////////////////////////

    @Override
    protected String doEncode(final Object object) {
        // for dotnet compatibility - toString pre 1.3 was equivalent to
        // toPlainString later.
        try {
            final Class<?> type = object.getClass();
            try {
                return (String) type.getMethod("toPlainString", (Class[]) null).invoke(object, (Object[]) null);
            } catch (final NoSuchMethodException nsm) {
                return (String) type.getMethod("toString", (Class[]) null).invoke(object, (Object[]) null);
            }
        } catch (final Exception e) {
            throw new IsisException(e);
        }

    }

    @Override
    protected BigDecimal doRestore(final String data) {
        return new BigDecimal(data);
    }

    // /////// toString ///////

    @Override
    public String toString() {
        return "BigDecimalValueSemanticsProvider: " + format;
    }

}