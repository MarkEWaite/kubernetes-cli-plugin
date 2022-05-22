package org.jenkinsci.plugins.kubernetes.cli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;

public class MultiKubectlBuildStep extends Step {
    @DataBoundSetter
    public List<KubectlCredential> kubectlCredentials;

    @DataBoundConstructor
    public MultiKubectlBuildStep(List<KubectlCredential> kubectlCredentials) {
        if (kubectlCredentials == null || kubectlCredentials.size() == 0) {
            throw new RuntimeException("[kubernetes-cli] credentials list cannot be empty");
        }
        this.kubectlCredentials = kubectlCredentials;
    }

    @Override
    public final StepExecution start(StepContext context) throws Exception {
        return new GenericBuildStep(this.kubectlCredentials, context);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Configure Kubernetes CLI (kubectl) with multiple credentials";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getFunctionName() {
            return "withKubeCredentials";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean takesImplicitBlockArgument() {
            return true;
        }

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return new HashSet<>();
        }

    }
}
