import AWS from '../components/assets/images/skills/aws.png'
import Django from '../components/assets/images/skills/django.png'
import Docker from '../components/assets/images/skills/docker.png'
import Figma from '../components/assets/images/skills/figma.png'
import Flutter from '../components/assets/images/skills/flutter.png'
import Java from '../components/assets/images/skills/java.png'
import Jenkins from '../components/assets/images/skills/jenkins.png'
import Jira from '../components/assets/images/skills/jira.png'
import JavaScript from '../components/assets/images/skills/js.png'
import Kotlin from '../components/assets/images/skills/kotlin.png'
import MongoDB from '../components/assets/images/skills/mongodb.png'
import Mysql from '../components/assets/images/skills/mysql.png'
import Nextjs from '../components/assets/images/skills/next.js.png'
import Nodejs from '../components/assets/images/skills/node.js.png'
import Python from '../components/assets/images/skills/python.png'
import React from '../components/assets/images/skills/react.png'
import Spring from '../components/assets/images/skills/spring.png'
import Swift from '../components/assets/images/skills/swift.png'
import TypeScript from '../components/assets/images/skills/typescript.png'
import net from '../components/assets/images/skills/net.png'
import Git from '../components/assets/images/skills/git.png'
import Unity from '../components/assets/images/skills/unity.png'

export const typeMap = new Map([
  ['PROJECT', '프로젝트'],
  ['STUDY', '스터디'],
  ['ONLINE', '온라인'],
  ['OFFLINE', '오프라인'],
  ['ONOFFLINE', '온오프라인'],
  ['Frontend', '프론트엔드'],
  ['Backend', '백엔드'],
])

export const imageMap: Record<any, any> = {
  Java,
  Spring,
  'Node.js': Nodejs,
  Kotlin,
  Python,
  Django,
  Mysql,
  MongoDB,
  net,
  JavaScript,
  React,
  TypeScript,
  'Next.js': Nextjs,
  Flutter,
  Swift,
  AWS,
  Docker,
  Figma,
  Git,
  Jenkins,
  Jira,
  Unity,
}

export const roleOptions: string[] = [
  'Backend',
  'Frontend',
  'Designer',
  'IOS',
  'Android',
  'PM',
  'QA',
  'GameDev',
  'DevOps',
]

export const dateFormat = (datetime: string) => {
  if (!datetime) return ''
  const date = new Date(datetime)
  const year = date.getFullYear()
  const month = ('0' + (date.getMonth() + 1)).slice(-2)
  const day = ('0' + date.getDate()).slice(-2)

  return `${year}-${month}-${day}`
}

export function daysUntil(datetime: string) {
  const today = new Date()
  today.setHours(0, 0, 0, 0)

  const targetDate = new Date(datetime)
  targetDate.setHours(0, 0, 0, 0)

  const difference = targetDate.getTime() - today.getTime()

  return Math.ceil(difference / (1000 * 60 * 60 * 24))
}
