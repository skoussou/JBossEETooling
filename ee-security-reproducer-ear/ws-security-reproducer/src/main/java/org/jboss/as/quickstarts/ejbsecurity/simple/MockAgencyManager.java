/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.ejbsecurity.simple;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the back-end resource for managing Agency bookings.
 * 
 * This is a mock implementation .
 * 
 * @author stkousso@redhat.com
 */
public class MockAgencyManager {

    // The singleton instance of this class.
    private static MockAgencyManager singletonInstance;

    // A thread safe booking counter
    private AtomicInteger bookings = new AtomicInteger(0);

    /**
     * Accessor to obtain the singleton Agency manager instance.
     * 
     * @return the singleton AgencyManager instance.
     */
    public synchronized static MockAgencyManager getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new MockAgencyManager();
        }

        return singletonInstance;
    }

    /**
     * Make a booking with the Agency.
     * 
     * @param txID The transaction identifier
     */
    public synchronized String makeBooking(String user, String password, String location) {
        System.out.println("[MOCK-SERVICE] makeBooking called on backend resource.");
        StringBuffer ref = new StringBuffer(String.valueOf(bookings.getAndIncrement())).append("-").append(location).append("-").append(user);
        return ref.toString();
    }

    /**
     * Returns the number of bookings
     * 
     * @return the number of bookings.
     */
    public int getBookingCount() {
        return bookings.get();
    }

    /**
     * Reset the booking counter to zero
     */
    public void reset() {
        bookings.set(0);
    }
}
