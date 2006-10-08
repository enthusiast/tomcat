/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.runtime;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
import javax.servlet.jsp.JspFactory;

import org.apache.jasper.compiler.Localizer;

/**
 * This is the super class of all JSP-generated servlets.
 *
 * @author Anil K. Vijendran
 */
public abstract class HttpJspBase 
    extends HttpServlet 
    implements HttpJspPage 
        
    
{
    
    static {
        if( JspFactory.getDefaultFactory() == null ) {
            JspFactoryImpl factory = new JspFactoryImpl();
            if( System.getSecurityManager() != null ) {
                String basePackage = "org.apache.jasper.";
                try {
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "runtime.JspFactoryImpl$PrivilegedGetPageContext");
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "runtime.JspFactoryImpl$PrivilegedReleasePageContext");
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "runtime.JspRuntimeLibrary");
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "runtime.JspRuntimeLibrary$PrivilegedIntrospectHelper");
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "runtime.ServletResponseWrapperInclude");
                    factory.getClass().getClassLoader().loadClass( basePackage +
                                                                   "servlet.JspServletWrapper");
                } catch (ClassNotFoundException ex) {
                    org.apache.juli.logging.LogFactory.getLog( HttpJspBase.class )
                        .error("Jasper JspRuntimeContext preload of class failed: " +
                                       ex.getMessage(), ex);
                }
            }
            JspFactory.setDefaultFactory(factory);
        }
    }

    protected HttpJspBase() {
    }

    public final void init(ServletConfig config) 
	throws ServletException 
    {
        super.init(config);
	jspInit();
        _jspInit();
    }
    
    public String getServletInfo() {
	return Localizer.getMessage("jsp.engine.info");
    }

    public final void destroy() {
	jspDestroy();
	_jspDestroy();
    }

    /**
     * Entry point into service.
     */
    public final void service(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
    {
        _jspService(request, response);
    }
    
    public void jspInit() {
    }

    public void _jspInit() {
    }

    public void jspDestroy() {
    }

    protected void _jspDestroy() {
    }

    public abstract void _jspService(HttpServletRequest request, 
				     HttpServletResponse response) 
	throws ServletException, IOException;
}
