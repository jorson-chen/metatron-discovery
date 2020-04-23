/*
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

package app.metatron.discovery.domain.activities;

import org.joda.time.DateTime;
import org.springframework.data.rest.core.config.Projection;

import app.metatron.discovery.domain.activities.spec.ActivityType;
import app.metatron.discovery.domain.activities.spec.Actor;

/**
 *
 */
public class ActivityStreamProjections {

  @Projection(types = ActivityStream.class, name = "default")
  public interface DefaultProjection {
    ActivityType getAction();

    String getActor();

    Actor.ActorType getActorType();

    String getObjectId();

    DateTime getPublishedTime();
  }

  @Projection(types = ActivityStream.class, name = "detail")
  public interface DetailProjection {
    ActivityType getAction();

    String getActor();

    Actor.ActorType getActorType();

    String getObjectId();

    ActivityStream.MetatronObjectType getObjectType();

    String getTargetId();

    ActivityStream.MetatronObjectType getTargetType();

    ActivityStream.GeneratorType getGeneratorType();

    String getGeneratorName();

    DateTime getPublishedTime();
  }

  @Projection(types = ActivityStream.class, name = "list")
  public interface ListProjection {
    ActivityType getAction();

    String getActor();

    String getObjectId();

    ActivityStream.MetatronObjectType getObjectType();

    String getTargetId();

    String getResult();

    String getGeneratorName();

    DateTime getPublishedTime();
  }

}
