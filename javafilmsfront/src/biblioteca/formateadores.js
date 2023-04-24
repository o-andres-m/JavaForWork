export function titleCase(str) {
    //null, undefined, and !str case
    if (typeof(str) !== 'string') return str;

    return str?.toString().trim().toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}