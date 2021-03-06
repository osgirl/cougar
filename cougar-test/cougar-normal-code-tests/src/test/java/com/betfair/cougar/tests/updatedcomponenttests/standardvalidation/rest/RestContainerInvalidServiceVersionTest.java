/*
 * Copyright 2013, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Originally from UpdatedComponentTests/StandardValidation/REST/Rest_Container_InvalidServiceVersion.xls;
package com.betfair.cougar.tests.updatedcomponenttests.standardvalidation.rest;

import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum;
import com.betfair.testing.utils.cougar.manager.AccessLogRequirement;
import com.betfair.testing.utils.cougar.manager.CougarManager;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that Cougar returns the correct DSC-0021 fault when a  REST XML/JSON request is made to a version of a Service that doesn't exist. Error should be "Not Found"
 */
public class RestContainerInvalidServiceVersionTest {
    @Test
    public void doTest() throws Exception {
        // Create the HttpCallBean
        CougarManager cougarManager = CougarManager.getInstance();

        // Set up the Http Call Bean to make the request
        HttpCallBean getNewHttpCallBean = cougarManager.getNewHttpCallBean("87.248.113.14");
        cougarManager = cougarManager;

        cougarManager.setCougarFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

        getNewHttpCallBean.setOperationName("testSimpleGet", "simple");

        getNewHttpCallBean.setServiceName("baseline", "cougarBaseline");
        // Point the request at a verions of the service that doesn't exist
        getNewHttpCallBean.setVersion("v0.1");

        Map map3 = new HashMap();
        map3.put("message","foo");
        getNewHttpCallBean.setQueryParams(map3);
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation (store the expected HTML response as a string)
        cougarManager.makeRestCougarHTTPCalls(getNewHttpCallBean);
        // Create the expected response as an XML document (Fault)
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers5.getXMLObjectFromString("<fault><faultcode>Client</faultcode><faultstring>DSC-0021</faultstring><detail/></fault>");
        // Convert the expected response to REST types for comparison with actual responses
        Map<CougarMessageProtocolRequestTypeEnum, Object> responseObjects = cougarManager.convertResponseToRestTypes(createAsDocument10, getNewHttpCallBean);
        // Check the 4 responses are as expected (Not Found error)
        HttpResponseBean response6 = getNewHttpCallBean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(responseObjects.get(CougarMessageProtocolRequestTypeEnum.RESTXML), response6.getResponseObject());
        AssertionUtils.multiAssertEquals(404, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Not Found", response6.getHttpStatusText());

        HttpResponseBean response7 = getNewHttpCallBean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(responseObjects.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), response7.getResponseObject());
        AssertionUtils.multiAssertEquals(404, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Not Found", response7.getHttpStatusText());

        HttpResponseBean response8 = getNewHttpCallBean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(responseObjects.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), response8.getResponseObject());
        AssertionUtils.multiAssertEquals(404, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Not Found", response8.getHttpStatusText());

        HttpResponseBean response9 = getNewHttpCallBean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(responseObjects.get(CougarMessageProtocolRequestTypeEnum.RESTXML), response9.getResponseObject());
        AssertionUtils.multiAssertEquals(404, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Not Found", response9.getHttpStatusText());
        // Check the log entries are as expected
        cougarManager.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9
                ,new AccessLogRequirement("87.248.113.14","/cougarBaseline/v0.1/simple","NotFound")
                ,new AccessLogRequirement("87.248.113.14","/cougarBaseline/v0.1/simple","NotFound")
                ,new AccessLogRequirement("87.248.113.14","/cougarBaseline/v0.1/simple","NotFound")
                ,new AccessLogRequirement("87.248.113.14","/cougarBaseline/v0.1/simple","NotFound")
        );
    }

}
