import Aws from "../components/assets/images/skills/aws.png";
import Django from "../components/assets/images/skills/django.png";
import Docker from "../components/assets/images/skills/docker.png";
import Figma from "../components/assets/images/skills/figma.png";
import Flutter from "../components/assets/images/skills/flutter.png";
import Java from "../components/assets/images/skills/java.png";
import Jenkins from "../components/assets/images/skills/jenkins.png";
import Jira from "../components/assets/images/skills/jira.png";
import JavaScript from "../components/assets/images/skills/js.png";
import Kotlin from "../components/assets/images/skills/kotlin.png";
import Mariadb from "../components/assets/images/skills/mariadb.png";
import Mongodb from "../components/assets/images/skills/mongodb.png";
import Mysql from "../components/assets/images/skills/mysql.png";
import Nextjs from "../components/assets/images/skills/next.js.png";
import Nodejs from "../components/assets/images/skills/node.js.png";
import Python from "../components/assets/images/skills/python.png";
import React from "../components/assets/images/skills/react.png";
import ReactNative from "../components/assets/images/skills/react.png";
import Spring from "../components/assets/images/skills/spring.png";
import Swift from "../components/assets/images/skills/swift.png";
import Thymeleaf from "../components/assets/images/skills/thymeleaf.png";
import TypeScript from "../components/assets/images/skills/typescript.png";

export const typeMap = new Map([
    ["project", "프로젝트"],
    ["study", "스터디"],
    ["online", "온라인"],
    ["offline", "오프라인"],
    ["Frontend", "프론트엔드"],
    ["Backend", "백엔드"],
]);

export const imageMap: Record<any, any> = {
    Aws,
    Django,
    Docker,
    Figma,
    Flutter,
    JavaScript,
    Kotlin,
    Jenkins,
    Jira,
    Java,
    Mariadb,
    Mongodb,
    Mysql,
    "Next.js": Nextjs,
    "Node.js": Nodejs,
    Python,
    React,
    Spring,
    Swift,
    Thymeleaf,
    TypeScript,
    ReactNative,
};

export const dateFormat = (datetime: string) => {
    if (!datetime) return "";
    const date = new Date(datetime);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);

    return `${year}-${month}-${day}`;
};
