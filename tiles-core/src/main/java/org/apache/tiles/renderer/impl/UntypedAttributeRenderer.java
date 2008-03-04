/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tiles.renderer.impl;

import java.io.IOException;
import java.io.Writer;

import org.apache.tiles.Attribute;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.awareness.TilesContainerAware;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.renderer.RendererException;

/**
 * Renders an attribute that has no associated renderer.
 *
 * @version $Rev$ $Date$
 * @since 2.1.0
 */
public class UntypedAttributeRenderer extends AbstractBaseAttributeRenderer
        implements TilesContainerAware {

    /**
     * The Tiles container.
     */
    private TilesContainer container;

    /** {@inheritDoc} */
    public void setContainer(TilesContainer container) {
        this.container = container;
    }

    /** {@inheritDoc} */
    @Override
    public void write(Object value, Attribute attribute,
            Writer writer, TilesRequestContext request, Object... requestItems)
            throws IOException, TilesException {
        if (value instanceof String) {
            String valueString = (String) value;
            if (container.isValidDefinition(valueString, requestItems)) {
                container.render(valueString, requestItems);
            } else if (valueString.startsWith("/")) {
                request.dispatch(valueString);
            } else {
                writer.write(valueString);
            }
        } else {
            throw new RendererException(
                    "Cannot render an untyped object attribute");
        }
    }
}
