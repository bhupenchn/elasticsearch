/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.seqno;

import org.elasticsearch.test.ESTestCase;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;

public class SequenceNumbersTests extends ESTestCase {

    public void testMin() {
        final long seqNo = randomNonNegativeLong();
        assertThat(SequenceNumbers.min(SequenceNumbersService.NO_OPS_PERFORMED, seqNo), equalTo(seqNo));
        assertThat(
                SequenceNumbers.min(SequenceNumbersService.NO_OPS_PERFORMED, SequenceNumbersService.UNASSIGNED_SEQ_NO),
                equalTo(SequenceNumbersService.UNASSIGNED_SEQ_NO));
        assertThat(SequenceNumbers.min(SequenceNumbersService.UNASSIGNED_SEQ_NO, seqNo), equalTo(seqNo));
        final long minSeqNo = randomNonNegativeLong();
        assertThat(SequenceNumbers.min(minSeqNo, seqNo), equalTo(Math.min(minSeqNo, seqNo)));

        final IllegalArgumentException e =
                expectThrows(IllegalArgumentException.class, () -> SequenceNumbers.min(minSeqNo, SequenceNumbersService.UNASSIGNED_SEQ_NO));
        assertThat(e, hasToString(containsString("sequence number must be assigned")));
    }

    public void testMax() {
        final long seqNo = randomNonNegativeLong();
        assertThat(SequenceNumbers.max(SequenceNumbersService.NO_OPS_PERFORMED, seqNo), equalTo(seqNo));
        assertThat(
                SequenceNumbers.max(SequenceNumbersService.NO_OPS_PERFORMED, SequenceNumbersService.UNASSIGNED_SEQ_NO),
                equalTo(SequenceNumbersService.UNASSIGNED_SEQ_NO));
        assertThat(SequenceNumbers.max(SequenceNumbersService.UNASSIGNED_SEQ_NO, seqNo), equalTo(seqNo));
        final long maxSeqNo = randomNonNegativeLong();
        assertThat(SequenceNumbers.min(maxSeqNo, seqNo), equalTo(Math.min(maxSeqNo, seqNo)));

        final IllegalArgumentException e =
                expectThrows(IllegalArgumentException.class, () -> SequenceNumbers.min(maxSeqNo, SequenceNumbersService.UNASSIGNED_SEQ_NO));
        assertThat(e, hasToString(containsString("sequence number must be assigned")));
    }

}
