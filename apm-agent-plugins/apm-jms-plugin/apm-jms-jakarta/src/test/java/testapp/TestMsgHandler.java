/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package testapp;


import co.elastic.apm.agent.impl.ElasticApmTracer;
import co.elastic.apm.agent.impl.transaction.TransactionImpl;
import co.elastic.apm.agent.tracer.GlobalTracer;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import java.util.concurrent.atomic.AtomicReference;

// won't be instrumented by default as it does not fit naming conventions that we rely on by default
public class TestMsgHandler implements MessageListener {

    private final AtomicReference<TransactionImpl> transaction;

    public TestMsgHandler(AtomicReference<TransactionImpl> transaction) {
        this.transaction = transaction;
    }

    @Override
    public void onMessage(Message message) {
        transaction.set(GlobalTracer.get().require(ElasticApmTracer.class).currentTransaction());
    }
}
