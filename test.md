## Universe Workshop Definition

## Track: Automate
9:00 am / Tuesday Nov 12

Presenters: Hector Alfaro, Eric Hollenberry


## Code: The foundation of DevOps pipelines

This workshop is designed to allow GitHub beginners to get comfortable with the skills needed in future workshops, without being too rudimentary for experienced GitHub professionals. The workshop will focus on GitHub's core capabilities but present them against the backdrop of DevOps best practices. Students will walk away with skills to be more intentional about organizing workflows and taxonomies for subsequent DevOps automation. You'll hear about:

- The importance of version control and code organization
- Using the GitHub workflow to govern team behavior and achieve cultural change
- How branching and merge strategy affect pull attributes and downstream automation
- The continuous integration approach and how branching may be directed to lower environments for testing and pre-production
- Code and feature tracking throughout the release process and back
- Setting up repositories to deploy to multiple infrastructures including multicloud and hybrid cloud environments.

## The importance of version control and code organization

We will want to ground version control as a necessary precursor to effective devops. The following is an excerpt that captures the top three issues.

### 3 Reasons Why VCS is Critical for DevOps

> ### Avoiding Dependency Issues

> Avoiding dependency issues in modern containerized applications
Microservices have essentially become the default for the development of new applications, and more and more teams are containerizing monolithic applications as well. With this trend, dependency issues have entered center stage as something that can make or break a project. 

> In any kind of project, whether containerized or not, dependency has always been a big concern. Dependency hell, affectionately called JAR hell in Java, is common in applications across multiple languages and has been since early programming days. Eventually, the linear path of building new features and fixing bugs is bound to clash with the parallel development of another feature by another team or hotfixes or a number of other things.

> For DevOps, finding the balance between moving quickly and maintaining application reliability is crucial. Part of that means cultivating traceability, gaining visibility into the changes made to the code and understanding how those changes affected application performance. Access to the different code iterations can reveal where new changes exposed dependencies that clash with other parts of the code.

> ### Version Control and DevOps Performance

> Version control is tied to higher DevOps performance
The annual State of DevOps study found, in 2014, that “version control was consistently one of the highest predictors of performance.” Top performing engineering teams were able to achieve higher throughput – 8x deployment frequency and 8000x faster deployment lead times.

> Since these original findings in 2014, version control hasn’t lost its significance relative to performance. Last year, it was again tied to success in Continuous Delivery workflows, which in turn contribute to higher IT performance.

> What’s causing the high correlation between version control and high performance? Well, it’s partially related to the ability to more easily view and understand how changes to one part of the code caused problem across the application. But, it has more to do with how the VCS enables coding practices like Continuous Integration and, as a result, Continuous Delivery/Deployment.

> ### Building More Reliable Applications

> Supports building more reliable applications
The same study from 2014 found that applications built with comprehensive version control systems were more reliable. They experienced 50% lower change failure rates and 12x faster MTTR (issue resolution time).

> It’s not hard to draw the connections between high performing DevOps teams and reliable applications. DevOps’ role in any organization is to enable successful advancements, and because change is often the source of failures, there’s a big focus on mitigating the risk of change.

> Although it may seem counterintuitive at first, the first step is usually making smaller changes more frequently (hence the popularity of CI). Just as important, and even more so in an accelerated workflow, is tracking those changes so that everyone is looking at the same thing and so that troubleshooting is easier in the case of failures.

> Source: https://blog.overops.com/3-reasons-why-version-control-is-a-must-for-every-devops-team/

## Using the GitHub workflow to govern team behavior and achieve cultural change

Most DevOps coaches and mentors will emphasize that DevOps is not about tooling or even process and practice. The emphasis is on cultural change where individuals have a deep grounding in 'why' they practice devops, prior to the 'how' they practice devops.

However, practically speaking, tooling is what inspires and instils disiplined behavior amongst diverse cross-functional teams. And being that this is a vendor led workshop based on a particular product, GitHub, it is expected that we would demonstrate DevOps Practice with our tooling.

Using the GitHub Flow we may establish a foundation for the primary aims of Devops. Logan Dagle of Collabnet, a leading Agile lifecycle management vendor writes:

> The primary DevOps goal is to optimize the flow of value from idea to end user. Obviously, there’s a cultural change that must happen for a company to be successful with DevOps, so culture is a big focus, but the DevOps goal is to make the delivery of value more efficient and effective.

> ...

> As we measure our end-to-end pipeline and begin to improve it, we are addressing the fundamental DevOps goal and that is to 1) break down silos, 2) create cross-functional teams, and 3) improve the flow of value.

> https://resources.collab.net/blogs/what-is-the-primary-goal-of-devops

While every organization is different, established large enterprises have typically separated devlopment from quality assurance, security, risk management, engineering, and operations. It is important to establish the GitHub Flow as a necessarily simplified and transparent means of organizing team behaviors.

### GitOps

The subsequent Continuous Integration and Continuous Delivery workshops in the 'Automate Track' will be demonstrating the use of the GitHub repository for both applications source code and infrastructure definition code. In their recent work "The DevOps Handbook" the authors write:

### Version Control 
> The comprehensive use of version control is relatively uncontroversial. We asked if respondents were keeping application code, system configuration, application configuration, and scripts for automating build and configuration in version control. These factors together predict IT performance and form a key component of continuous delivery. What was most interesting was that keeping system and application configuration in version control was more highly correlated with software delivery performance than keeping application code in version control. Configuration is normally considered a secondary concern to application code in configuration management, but our research shows that this is a misconception.

> Forsgren PhD, Nicole. Accelerate . IT Revolution Press. Kindle Edition. 

It will be important to explain that while we recommend GitHub Flow for source code maintainance, we not explain it solely as applicable to application source code, and additionally mention the need to version infrastructure-related code as well.

### Trunk Based Development

GitHub Flow is an implementation of Trunk Based development. While we utilize the terminology of 'master' often when referring to the base branch beneath HEAD, it is important to tie back to leading authors such as Gene Kim and Jezz HUmble that typically utilize the term 'Trunk'. Most well read DevOps practitioners that may still be unfamiliar with GitHub will use this terminology.

> Our research also found that developing off trunk/master rather than on long-lived feature branches was correlated with higher delivery performance. Teams that did well had fewer than three active branches at any time, their branches had very short lifetimes (less than a day) before being merged into trunk and never had “code freeze” or stabilization periods. 

> It’s worth re-emphasizing that these results are independent of team size, organization size, or industry. Even after finding that trunk-based development practices contribute to better software delivery performance, some developers who are used to the “GitHub Flow” workflow remain skeptical. This workflow relies heavily on developing with branches and only periodically merging to trunk. 

> We have heard, for example, that branching strategies are effective if development teams don’t maintain branches for too long—and we agree that working on short-lived branches that are merged into trunk at least daily is consistent with commonly accepted continuous integration practices. 

> We conducted additional research and found that teams using branches that live a short amount of time (integration times less than a day) combined with short merging and integration periods (less than a day) do better in terms of software delivery performance than teams using longer-lived branches. 

> Anecdotally, and based on our own experience, we hypothesize that this is because having multiple long-lived branches discourages both refactoring and intrateam communication. We should note, however, that GitHub Flow is suitable for open source projects whose contributors are not working on a project full time. In that situation, it makes sense for branches that part-time contributors are working on to live for longer periods of time without being merged.

> Forsgren PhD, Nicole. Accelerate . IT Revolution Press. Kindle Edition. 

Because of the perception by some that GitHub Flow inspires longer-living branches that we do in fact encourage frequent commit/push and pull request cadences to at a minimum facilitate a daily build.

### Technical Knowledge of GitHub Flow

The technical aspects of GitHub Flow that should be understood by students that will be proceeding to the CI/CD and CDRA workshops are:

```
- Fork an existing repo
- How to configure local workstation git with user.name and user.email
- How to create a protected master branch to prevent forced pushes from updating master directly without a pull request
- Understand code organization in folders and how language may influce repo hierarchies
- Understand the use of distributed version control and how a repo is cloned to a local workstation using git
- How to create a branch locally on the workstation using git checkout
- How to create a branch on the GitHub web interface
- How semantic naming is used to name release branches
- How to create a local file, or modify an existing file
- How git status shows local files added or modified in red prior to their being added for a subsquent commit. 
- A brief discussion of work in progress, staged, and committed changes
- How a git commit command functions
- How the git push command is used to push changes to the central repository based on the feature branch that is checked out
- How to create a Pull Request and use it as a means of collaboration and review
- How to assign reviewers to a Pull Request
- How to approve a Pull Request
- How to Merge code to master once a Pull Request is Approved
```

### Merge Conflicts

It will be important to discuss merge conflicts and how they occur. The remediation of merge conflicts should be covered using the 'reverse merge' capability in a local sandbox with git fetch and git merge.

Another important demonstration would be to show how the Pull Request identifies merge conflicts and enables their resolution on the GitHub web interface.

### Rebasing

A brief discussion of rebasing may prove benficial and how it differs from conventional merging, as well as why it would be discouraged due to the elimination of discreet commits.

## How branching and merge strategy affect pull attributes and downstream automation

### Continuous Delivery vs Continuous Deployment

It will be important to clarify the difference between Continuous Delivery and Continuous Deployment. These terms have been misunderstood and often misused in the DevOps community. In our Continuous Delivery workshop we will not be encouraging continuous deployment because most enterprise organizations require release gating for GRC (Governance, Risk Management and Compliance). The CDRA workshop will demonstrate these concepts thoroughly.

Martin Fowler, a leading thought leader on DevOps clarifies this issue in this way:

> Continuous Delivery is a software development discipline where you build software in such a way that the software can be released to production at any time.

> You’re doing continuous delivery when:

> - Your software is deployable throughout its lifecycle
> - Your team prioritizes keeping the software deployable over working on new features
> - Anybody can get fast, automated feedback on the production readiness of their systems any time somebody makes a change to them
> - You can perform push-button deployments of any version of the software to any environment on demand

>You achieve continuous delivery by continuously integrating the software done by the development team, building executables, and running automated tests on those executables to detect problems. Furthermore you push the executables into increasingly production-like environments to ensure the software will work in production. To do this you use a DeploymentPipeline.
>
> ...
>  
> Continuous Delivery is sometimes confused with Continuous Deployment. Continuous Deployment means that every change goes through the pipeline and automatically gets put into production, resulting in many production deployments every day. Continuous Delivery just means that you are able to do frequent deployments but may choose not to do it, usually due to businesses preferring a slower rate of deployment. In order to do Continuous Deployment you must be doing Continuous Delivery.

### The Significance of the Pull Request for Downstream DevOps

It will be important to emphasize the use of the Pull Request to trigger the downstream CI/CD functionality. While it is not etched in stone, it is suggested that we demonstrate a Build Trigger on each push, and show how the Pull Request reports build status back to developers and stakeholders.

Upon the Pull Request approval we may demonstrate how that action may trigger a deployment to either a production or pre-production infrastructure.

Finally, if we are teaching Continuous Delivery, we will need to show how, master must always be in a deployable state. For this reason it is my understanding that only after we have tested the feature branch in production, should we then allow the merge to master. This means that what is deployed on the Pull Request approval is the Feature Branch. 

This means of protecting the master branch and merging only after feature releases are approved with production use as feedback, enables the master to be used for rollback in the event of a failed feature release.

### Webhooks vs. Actions Workflow

Prior to the release of Actions v2, most organizations would utilize webhooks to trigger the automated build or deployment activities. With Actions v2 (DreamLifter) the workflow defined within the repo is used, and the "on <event>" line in the workflow signifies what actions to take based on which events.

We will be building a number of sample workflows for the CI, CD, and CDRA workshops so, while we do not want to teach the particular ways the workflows are created (that will be done in the subsequent workshops) we do want to show the students the workflows so they can understand how the branching and merge strategies fit into pipeline automation.

## The continuous integration approach and how branching may be directed to lower environments for testing and pre-production

While Continuous Integration mandates that code be built and unit tests be run upon each push from a local sandbox, it is important to distinguish the workflow required to validate the code changes, verses the workflow required to actually enable deployment.

In most enterprise environments, dependent upon application architecture and governance and regulation, deploying directly to production is not supported. For this reason, and due to legacy pre-devops practice, organizations think of the 'promotion' of workloads to production.

The lower environments are often dev and test, and the upper environments are known as staging and prod. The 'push to prod' is the pinnacle aim of the development team when production feedback is required for a stringent 'definition of done'.

To achive this, Action workflows will be created to use data and naming conventions to know whether deployment is to a lower environment or an upper one. While some organizations such as GitHub use manual Hubot skills to deploy, the pipelines for the CD and CDRA courses will be fully automated and instead make decisions within the pipeline based on parameters available from the repo itself.

This will become clear once we have completed the engineering of the pipelines themselves, and this section will be updated with specific examples once that work is complete.

## Code and feature tracking throughout the release process and back

Most organizations that have implemented Agile utilize an Agile lifecycle management tool such as Atlassian Jira, Rally, or VersionOne. These tools involve the practice of managing a Product Backlog, Sprint Backlogs, and in some cases Kan-Ban Boards to track progress.

In any case, the use of epics and user stories is a means of codifying the changes that go into specific releases. Many leading organizations such as the Kubernetes Project keep the release changes in a markdown file within the repo. Each semantic nomenclature for the major and minor releases is codified in these files. This is important to be able to maintain the changes included in any feature branch in a human-readable fashion.

Oftentimes large enterprises transmit the manifests, or release notes with the build artifacts as they are elevated in the delivery pipeline. This enables a person in operations, long after deployment, to access features changed when evaluating any particular release that is in production.

Monitoring tools, alerting and dashboards run in production often report the names of executables, containers, pods, or other runtime modules and the naming of these should allow the observer to know which specific release is relevant. This is known as 'traceability' through the pipeline and should be discussed in the VCS workshop.

The actiual use of the 'Releases' capability as well as tagging in GitHub will be demonstrated and taught in the subsequent workshops. This is another topic that should merely be mentioned here to gain better context for the subsequent learning.

## Setting up repositories to deploy to multiple infrastructures including multicloud and hybrid cloud environments.

We are still engineering a repo and workflow that implements Intuit's use case using GitHub and GitOps. Some of the techniques utilized in their process will be recreated for the CI/CD and CDRA workshops. Once these have been completed, they will be shared back to this VCS Workshop definition to denote the impact on the material covered specifically within this workshop. At the time of this draft, specific explanations of this capability remain incomplete.

## Other Considerations

While much of the material to teach this course is already available in other learning labs, videos and documents, it is important to weave the DevOps learning into the material so that the work product from this VCS Workshop may then be used in GitHubs upcoming DevOps Certification Program.

For this reason it is important to develop a bibliography of third-party resources that substantiate the DevOps 'Best Practices' being promoted by GitHub. As presenters develop workshop content, it is important to consider the key thought-leaders and authors in the DevOps community and build upon their work rather than deviate from it. We will also want to provide workshop attendees a "For Further Study" list of resources to allow them to continue their learning after the workshops have been conducted.

Realizing that GitHub as a tooling vendor will have its own point of view on best practices, it is apparent that Actions v2 and its implementation by leading GitHub clients, will greatly influence what we evolve toward as GitHub best practice.


## Summation

This markdown document has been provided to allow the presenters of the VCS Workshop to begin work on the content and deliverables for that workshop. The GitHub Flow is the primary content and the presentation of that content is largely consistent with GitHub standards and norms.

The other more contextural aspects of this workshop, that is the 'backdrop of DevOps' will become more clear once the Actions v2 Pipelines have been created and are demonstrable.

At this time, the most productive work that may be done is:

- Any revisions to this text that are necessary to gain consensus
- Gathering of existing GitHub Flow training materials to determine suitability for reuse
- Collaborative conversations to determine which team members will be able to produce which specific content that is required for the workshop (ie. Slides, Documents, Learnin Labs, etc.)

