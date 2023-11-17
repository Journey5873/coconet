export const dummyData = {
    articleId: 1,
    title: "new_test_title",
    content: "new_test_content",
    createdAt: "2023-09-30T04:37:23",
    updateAt: "2023-09-30T04:37:23",
    plannedStartAt: "2023-11-24T17:11:32",
    expiredAt: "2023-10-11T09:57:49",
    estimatedDuration: "4개월미만",
    viewCount: 0,
    bookmarkCount: 0,
    articleType: "project",
    status: 1,
    meetingType: "online",
    author: "tester9090",
    articleRoleDtos: [
        {
            roleName: "Backend",
            participant: 3,
        },
        {
            roleName: "Frontend",
            participant: 3,
        },
    ],
    articleStackDtos: [
        {
            stackName: "Java",
            category: "backend",
            image: "java.png",
        },
        {
            stackName: "Spring",
            category: "backend",
            image: "spring.png",
        },
        {
            stackName: "Next.js",
            category: "front-end",
            image: "nextjs.png",
        },
        {
            stackName: "Mysql",
            category: "backend",
            image: "mysql.png",
        },
    ],
};

export type DummyData = typeof dummyData;
