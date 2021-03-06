package org.apache.maven.execution;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.monitor.event.EventDispatcher;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.profiles.ProfileManager;
import org.apache.maven.project.DefaultProjectBuilderConfiguration;
import org.apache.maven.project.ProjectBuilderConfiguration;
import org.apache.maven.settings.Settings;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id$
 */
public class DefaultMavenExecutionRequest
    implements MavenExecutionRequest
{
    /**
     * @todo [BP] is this required? This hands off to MavenSession, but could be passed through the handler.handle function (+ createSession).
     */
    private final ArtifactRepository localRepository;

    private final List goals;

    protected MavenSession session;

    private final EventDispatcher eventDispatcher;

    private final Settings settings;

    private final String baseDirectory;

    private boolean recursive = true;

    private boolean reactorActive;

    private String pomFilename;

    private String failureBehavior;

    private final ProfileManager globalProfileManager;

    private final Properties executionProperties;

    private final Properties userProperties;

    private final Date startTime;

    private final boolean showErrors;
    
    private String makeBehavior;
    
    private String resumeFrom;
    
    private List selectedProjects;

    // lazily initialized.
    private ProjectBuilderConfiguration projectBuilderConfig;

    public DefaultMavenExecutionRequest( ArtifactRepository localRepository, Settings settings,
                                         EventDispatcher eventDispatcher, List goals, String baseDirectory,
                                         ProfileManager globalProfileManager, Properties executionProperties,
                                         Properties userProperties,
                                         boolean showErrors )
    {
        this.localRepository = localRepository;

        this.settings = settings;

        this.goals = goals;

        this.eventDispatcher = eventDispatcher;

        this.baseDirectory = baseDirectory;

        this.globalProfileManager = globalProfileManager;

        this.executionProperties = executionProperties;

        this.userProperties = userProperties;

        startTime = new Date();

        this.showErrors = showErrors;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public String getBaseDirectory()
    {
        return baseDirectory;
    }

    public boolean isRecursive()
    {
        return recursive;
    }

    public void setRecursive( boolean recursive )
    {
        this.recursive = false;
    }

    public ArtifactRepository getLocalRepository()
    {
        return localRepository;
    }

    public List getGoals()
    {
        return goals;
    }

    public Properties getExecutionProperties()
    {
        return executionProperties;
    }

    // ----------------------------------------------------------------------
    // Putting the session here but it can probably be folded right in here.
    // ----------------------------------------------------------------------

    public MavenSession getSession()
    {
        return session;
    }

    public void setSession( MavenSession session )
    {
        session = session;
    }

    public void addEventMonitor( EventMonitor monitor )
    {
        eventDispatcher.addEventMonitor( monitor );
    }

    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }

    public void setReactorActive( boolean reactorActive )
    {
        this.reactorActive = reactorActive;
    }

    public boolean isReactorActive()
    {
        return reactorActive;
    }

    public void setPomFile( String pomFilename )
    {
        this.pomFilename = pomFilename;
    }

    public String getPomFile()
    {
        return pomFilename;
    }

    public void setFailureBehavior( String failureBehavior )
    {
        this.failureBehavior = failureBehavior;
    }

    public String getFailureBehavior()
    {
        return failureBehavior;
    }

    public ProfileManager getGlobalProfileManager()
    {
        return globalProfileManager;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public boolean isShowErrors()
    {
        return showErrors;
    }

    public Properties getUserProperties()
    {
        return userProperties;
    }

    public ProjectBuilderConfiguration getProjectBuilderConfiguration()
    {
        if ( projectBuilderConfig == null )
        {
            ProjectBuilderConfiguration config = new DefaultProjectBuilderConfiguration();
            config.setLocalRepository( getLocalRepository() )
                  .setGlobalProfileManager( getGlobalProfileManager() )
                  .setExecutionProperties( getExecutionProperties() )
                  .setUserProperties( getUserProperties() )
                  .setBuildStartTime( startTime );
            
            projectBuilderConfig = config;
        }

        return projectBuilderConfig;
    }
    
    public String getMakeBehavior()
    {
        return makeBehavior;
    }

    public void setMakeBehavior( String makeBehavior )
    {
        this.makeBehavior = makeBehavior;
    }

    public String getResumeFrom()
    {
        return resumeFrom;
    }

    public void setResumeFrom( String resumeFrom )
    {
        this.resumeFrom = resumeFrom;
    }

    public List getSelectedProjects()
    {
        return selectedProjects;
    }

    public void setSelectedProjects( List selectedProjects )
    {
        this.selectedProjects = selectedProjects;
    }

}
