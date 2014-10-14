/*
 * Copyright 2013-2014 eBay Softwarimport net.hydromatic.avatica.AvaticaConnection;
import net.hydromatic.avatica.AvaticaResultSet;
import net.hydromatic.avatica.AvaticaStatement;
ense.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kylinolap.kylin.jdbc;

import net.hydromatic.avatica.AvaticaConnection;
import net.hydromatic.avatica.AvaticaResultSet;
import net.hydromatic.avatica.AvaticaStatement;

/**
 * Kylin statement implementation
 * 
 * @author xduo
 * 
 */
public abstract class KylinStatementImpl extends AvaticaStatement {

    protected KylinStatementImpl(AvaticaConnection connection, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        super(connection, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    protected void close_() {
        if (!closed) {
            closed = true;
            final KylinConnectionImpl connection_ = (KylinConnectionImpl) connection;
            connection_.statements.remove(this);
            if (openResultSet != null) {
                AvaticaResultSet c = openResultSet;
                openResultSet = null;
                c.close();
            }
            // If onStatementClose throws, this method will throw an exception
            // (later
            // converted to SQLException), but this statement still gets closed.
            connection_.getDriver().handler.onStatementClose(this);
        }
    }
}
